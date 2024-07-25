package com.moingay.cricketsuper.presentation.ui.series

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
import com.moingay.cricketsuper.domain.navigation.Destination
import com.moingay.cricketsuper.domain.navigation.tabslivedetail.SeriesTabs
import com.moingay.cricketsuper.presentation.ui.components.LoadingData
import com.moingay.cricketsuper.presentation.ui.components.PullToRefreshLazyColumnCricketSuper
import com.moingay.cricketsuper.presentation.ui.home.components.TrendingSeriesItem
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import com.moingay.cricketsuper.utils.CompetitionStatus
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesScreen(navController: NavHostController) {
    val seriesViewModel: SeriesViewModel = koinViewModel()
    val uiState by seriesViewModel.stateFlow.collectAsState()

    // TODO UI Rendering
    SeriesScreen(
        modifier = Modifier.fillMaxSize(),
        listOngoing = uiState.ongoingCompetition?.response?.items ?: emptyList(),
        listUpcoming = uiState.upcomingCompetition?.response?.items ?: emptyList(),
        listCompleted = uiState.completedCompetition?.response?.items ?: emptyList(),
        onInitPagerCompetitions = {
            when (it) {
                SeriesTabs.Completed -> seriesViewModel.onInitPagerCompetitions(CompetitionStatus.RESULT)
                SeriesTabs.Ongoing -> seriesViewModel.onInitPagerCompetitions(CompetitionStatus.LIVE)
                SeriesTabs.Upcoming -> seriesViewModel.onInitPagerCompetitions(CompetitionStatus.FIXTURE)
            }
        },
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = {
            when (it) {
                SeriesTabs.Completed -> seriesViewModel.onRefresh(CompetitionStatus.RESULT)
                SeriesTabs.Ongoing -> seriesViewModel.onRefresh(CompetitionStatus.LIVE)
                SeriesTabs.Upcoming -> seriesViewModel.onRefresh(CompetitionStatus.FIXTURE)
            }
        },
        onClickItem = {
            navController.navigate(Destination.SeriesDetailScreen(it.cid ?: 0, it.title ?: ""))
        }
    )

}

@Composable
fun SeriesScreen(
    modifier: Modifier = Modifier,
    listOngoing: List<Competition> = emptyList(),
    listUpcoming: List<Competition> = emptyList(),
    listCompleted: List<Competition> = emptyList(),
    onInitPagerCompetitions: (SeriesTabs) -> Unit = {/* no-op */ },
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (SeriesTabs) -> Unit = {/* no-op */ },
    onClickItem: (Competition) -> Unit = {/* no-op */ }
) {
    val seriesTabs = listOf(
        SeriesTabs.Ongoing,
        SeriesTabs.Upcoming,
        SeriesTabs.Completed
    )

    val pagerState = rememberPagerState(
        pageCount = { seriesTabs.size },
        initialPage = 0,
    )
    LaunchedEffect(key1 = pagerState.currentPage) {
        onInitPagerCompetitions(seriesTabs[pagerState.currentPage])
    }

    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .zIndex(1f)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            BackgroundColorAppFirst,
                            BackgroundColorAppSecond
                        )
                    )
                )
        ) {
            Text(
                text = "All Series",
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
                seriesTabs.forEachIndexed { index, tabs ->
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
                    onRefresh(seriesTabs[indexPager])
                }) {
                when (seriesTabs[indexPager]) {
                    SeriesTabs.Ongoing -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(listOngoing, key = { it.cid ?: 0 }) { competition ->
                                TrendingSeriesItem(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    competition = competition,
                                    onClickItem = onClickItem,
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White.copy(0.8f)
                                    )
                                )
                            }
                        }
                    }

                    SeriesTabs.Upcoming -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(listUpcoming, key = { it.cid ?: 0 }) { competition ->
                                TrendingSeriesItem(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    competition = competition,
                                    onClickItem = onClickItem,
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White.copy(0.8f)
                                    )
                                )
                            }
                        }
                    }

                    SeriesTabs.Completed -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(listCompleted, key = { it.cid ?: 0 }) { competition ->
                                TrendingSeriesItem(
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    competition = competition,
                                    onClickItem = onClickItem,
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White.copy(0.8f)
                                    )
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
    SeriesScreen(
        modifier = Modifier.fillMaxSize()
    )
}
