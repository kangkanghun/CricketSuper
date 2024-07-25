package com.moingay.cricketsuper.presentation.ui.series.seriesdetail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.domain.navigation.Destination
import com.moingay.cricketsuper.domain.navigation.tabslivedetail.SeriesDetailTabs
import com.moingay.cricketsuper.presentation.ui.components.LoadingData
import com.moingay.cricketsuper.presentation.ui.home.components.CricketMatchCardLive
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesDetailScreen(navController: NavHostController) {
    // TODO UI Rendering
    val seriesDetailViewModel: SeriesDetailViewModel = koinViewModel()
    val uiState by seriesDetailViewModel.stateFlow.collectAsState()

    SeriesDetailScreen(
        modifier = Modifier.fillMaxSize(),
        listLive = uiState.pagingDataItem.collectAsLazyPagingItems(),
        onBack = { navController.popBackStack() },
        onClickItemMatch = {
            navController.navigate(
                Destination.LiveMatchDetailScreen(it.matchId ?: 0)
            )
        },
        titleSeries = uiState.titleSeries
    )
}

@Composable
fun SeriesDetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {/* no-op */ },
    listLive: LazyPagingItems<Item>? = null,
    onClickItemMatch: (Item) -> Unit = {/* no-op */ },
    titleSeries: String = "",
) {
    val seriesDetailTabs = listOf(
        SeriesDetailTabs.Fixture,
    )
    val pagerState = rememberPagerState(
        pageCount = { seriesDetailTabs.size },
        initialPage = 0,
    )
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = titleSeries,
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
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
                seriesDetailTabs.forEachIndexed { index, tabs ->
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
                    )
                }
            }
            HorizontalPager(state = pagerState) {
                when (seriesDetailTabs[it]) {
                    SeriesDetailTabs.Fixture -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White.copy(0.8f)),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                listLive?.itemCount ?: 0,
                                key = { index ->
                                    listLive?.get(index)?.matchId ?: 0
                                }
                            ) { index ->
                                listLive?.get(index)?.let { it1 ->
                                    CricketMatchCardLive(
                                        modifier = Modifier.fillMaxWidth(),
                                        item = it1,
                                        onClickItem = onClickItemMatch
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = listLive?.loadState?.refresh is LoadState.Loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadingData()
    }
}

@Composable
@Preview(name = "SeriesDetail", showSystemUi = true)
private fun SeriesDetailScreenPreview() {
    SeriesDetailScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        BackgroundColorAppFirst,
                        BackgroundColorAppSecond
                    )
                )
            )
    )
}
