package com.moingay.cricketsuper.presentation.ui.matches

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.domain.navigation.Destination
import com.moingay.cricketsuper.domain.navigation.tabslivedetail.MatchesTabs
import com.moingay.cricketsuper.presentation.ui.components.LoadingData
import com.moingay.cricketsuper.presentation.ui.components.PullToRefreshLazyColumnCricketSuper
import com.moingay.cricketsuper.presentation.ui.home.components.CricketMatchCardLive
import com.moingay.cricketsuper.presentation.ui.home.components.TrendingSeriesItem
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MatchesScreen(navController: NavHostController) {
    val matchesViewModel: MatchesViewModel = koinViewModel()
    val uiState by matchesViewModel.stateFlow.collectAsState()

    // TODO UI Rendering
    MatchesScreen(
        modifier = Modifier.fillMaxSize(),
        listLive = uiState.liveMatchData?.response?.items ?: emptyList(),
        listUpcoming = uiState.upcomingMatchData?.response?.items ?: emptyList(),
        listCompleted = uiState.completedMatchData?.response?.items ?: emptyList(),
        listAbandoned = uiState.abandonedMatchData?.response?.items ?: emptyList(),
        onInitPagerCompetitions = {
            when (it) {
                MatchesTabs.Live -> matchesViewModel.onInitPagerCompetitions(MatchStatus.LIVE)
                MatchesTabs.Upcoming -> matchesViewModel.onInitPagerCompetitions(MatchStatus.SCHEDULED)
                MatchesTabs.Completed -> matchesViewModel.onInitPagerCompetitions(MatchStatus.COMPLETED)
                MatchesTabs.Abandoned -> matchesViewModel.onInitPagerCompetitions(MatchStatus.ABANDONED)
            }
        },
        onClickItem = { item ->
            item.matchId?.let {
                navController.navigate(Destination.LiveMatchDetailScreen(it))
            }
        },
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = {
            when (it) {
                MatchesTabs.Live -> matchesViewModel.onRefresh(MatchStatus.LIVE)
                MatchesTabs.Upcoming -> matchesViewModel.onRefresh(MatchStatus.SCHEDULED)
                MatchesTabs.Completed -> matchesViewModel.onRefresh(MatchStatus.COMPLETED)
                MatchesTabs.Abandoned -> matchesViewModel.onRefresh(MatchStatus.ABANDONED)
            }
        }
    )

}

@Composable
fun MatchesScreen(
    modifier: Modifier = Modifier,
    listLive: List<Item> = emptyList(),
    listUpcoming: List<Item> = emptyList(),
    listCompleted: List<Item> = emptyList(),
    listAbandoned: List<Item> = emptyList(),
    onInitPagerCompetitions: (MatchesTabs) -> Unit = {/* no-op */ },
    onClickItem: (Item) -> Unit = {/* no-op */ },
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (MatchesTabs) -> Unit = {/* no-op */ },
) {
    val matchesTabs = listOf(
        MatchesTabs.Live,
        MatchesTabs.Upcoming,
        MatchesTabs.Completed,
        MatchesTabs.Abandoned
    )

    val pagerState = rememberPagerState(
        pageCount = { matchesTabs.size },
        initialPage = 0,
    )
    LaunchedEffect(key1 = pagerState.currentPage) {
        onInitPagerCompetitions(matchesTabs[pagerState.currentPage])
    }

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        Column(
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        BackgroundColorAppFirst,
                        BackgroundColorAppSecond
                    )
                )
            ).zIndex(1f)
        ) {
            Text(
                text = "All Matches",
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center
            )
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                divider = {/* no-op */ },
                indicator = { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(
                                tabPositions[pagerState.currentPage]
                            ),
                            color = Color.White
                        )
                    }
                }
            ) {
                matchesTabs.forEachIndexed { index, tabs ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = tabs.label,
                                fontSize = 14.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Gray,
                        icon = {
                            Icon(
                                painter = painterResource(id = tabs.resId),
                                contentDescription = null,
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    )
                }
            }
        }
        HorizontalPager(state = pagerState) { indexPager ->
            PullToRefreshLazyColumnCricketSuper(
                isRefreshing = isRefreshing,
                onRefresh = {
                    onRefresh(matchesTabs[indexPager])
                }
            ) {
                when (matchesTabs[indexPager]) {
                    MatchesTabs.Live -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(0.8f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(listLive, key = { it.matchId ?: 0 }) {
                                CricketMatchCardLive(
                                    modifier = Modifier.fillMaxWidth(),
                                    item = it,
                                    onClickItem = onClickItem
                                )
                            }
                        }
                    }

                    MatchesTabs.Upcoming -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(0.8f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(listUpcoming, key = { it.matchId ?: 0 }) {
                                CricketMatchCardLive(
                                    modifier = Modifier.fillMaxWidth(),
                                    item = it,
                                    onClickItem = onClickItem
                                )
                            }
                        }
                    }

                    MatchesTabs.Completed -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(0.8f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(listCompleted, key = { it.matchId ?: 0 }) {
                                CricketMatchCardLive(
                                    modifier = Modifier.fillMaxWidth(),
                                    item = it,
                                    onClickItem = onClickItem
                                )
                            }
                        }
                    }

                    MatchesTabs.Abandoned -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(0.8f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(listAbandoned, key = { it.matchId ?: 0 }) {
                                CricketMatchCardLive(
                                    modifier = Modifier.fillMaxWidth(),
                                    item = it,
                                    onClickItem = onClickItem
                                )
                            }
                        }
                    }

                }
            }
        }
    }

    AnimatedVisibility(
        visible = isLoading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadingData()
    }
}

@Composable
@Preview(name = "Series")
private fun SeriesScreenPreview() {
    MatchesScreen(
        modifier = Modifier.fillMaxSize()
    )
}
