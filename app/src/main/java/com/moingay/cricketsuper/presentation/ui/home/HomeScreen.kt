package com.moingay.cricketsuper.presentation.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFilter
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.Datum
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.data.model.response.Team
import com.moingay.cricketsuper.data.model.response.Toss
import com.moingay.cricketsuper.data.model.response.Venue
import com.moingay.cricketsuper.domain.navigation.Destination
import com.moingay.cricketsuper.domain.navigation.tabslivedetail.HomeTopTabs
import com.moingay.cricketsuper.presentation.ui.components.LoadingData
import com.moingay.cricketsuper.presentation.ui.components.PullToRefreshLazyColumnCricketSuper
import com.moingay.cricketsuper.presentation.ui.home.components.CricketMatchCard
import com.moingay.cricketsuper.presentation.ui.home.components.CricketMatchCardLive
import com.moingay.cricketsuper.presentation.ui.home.components.TopSeriesItem
import com.moingay.cricketsuper.presentation.ui.home.components.TrendingSeriesItem
import com.moingay.cricketsuper.presentation.ui.news.LoadingItem
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import com.moingay.cricketsuper.utils.Constants
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavHostController) {
    // TODO UI Rendering
    val homeViewModel: HomeViewModel = koinViewModel()
    val uiState by homeViewModel.stateFlow.collectAsState()

    HomeScreen(
        modifier = Modifier
            .fillMaxSize(),
        listTrendingSeries = (uiState.trendingSeries?.response?.items
            ?: emptyList()).fastFilter { it.status == "live" }.take(4),
        listLive = uiState.matchData?.response?.items ?: emptyList(),
        listTop = uiState.listTop,
        onClickItemLive = { item ->
            item.matchId?.let {
                navController.navigate(Destination.LiveMatchDetailScreen(it))
            }
        },
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = homeViewModel::onRefresh,
        news = uiState.pagingData.collectAsLazyPagingItems(),
        onClickItemTopStories = {
            it.attributes?.slug?.let { slug ->
                navController.navigate(Destination.NewsDetailScreen(slug))
            }
        },
        onClickViewMoreNews = {
            navController.navigate(Destination.NewsScreen.fullRoute)
        },
        onClickViewMoreSeries = {
            navController.navigate(Destination.SeriesScreen.fullRoute)
        },
        onClickItemTrendingSeries = {
            navController.navigate(Destination.SeriesDetailScreen(it.cid ?: 0, it.title ?: ""))
        },
        isFirsTime = uiState.isFirstTime,
        onDismissFirstTime = homeViewModel::setIsNotFirstTime,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    listTop: List<Item> = emptyList(),
    listTrendingSeries: List<Competition> = emptyList(),
    listDatum: List<Datum> = emptyList(),
    onClickItemTopStories: (Datum) -> Unit = {},
    onClickItemLive: (Item) -> Unit = {},
    listLive: List<Item> = emptyList(),
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    news: LazyPagingItems<Datum>,
    onClickViewMoreNews: () -> Unit = {/* no-op */ },
    onClickViewMoreSeries: () -> Unit = {/* no-op */ },
    onClickItemTrendingSeries: (Competition) -> Unit = {/* no-op */ },
    isFirsTime: Boolean = false,
    onDismissFirstTime: () -> Unit = {/* no-op */ },
) {
    Log.d("TAG", "HomeScreen: $listTop")
    val pagerState = rememberPagerState(
        pageCount = { 2 },
        initialPage = 0,
    )
    val pagerStateTop = rememberPagerState(
        pageCount = { listTop.size },
        initialPage = 0,
    )
    val tabs = listOf(HomeTopTabs.Home, HomeTopTabs.Live)
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            BackgroundColorAppFirst,
                            BackgroundColorAppSecond
                        )
                    )
                )
                .zIndex(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = null,
                    tint = Color.White
                )
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // TabRow with tabs
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
            ) {
                tabs.forEachIndexed { index, tabs ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = { Text(text = tabs.label, fontSize = 16.sp) },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            PullToRefreshLazyColumnCricketSuper(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh
            ) {
                if (page == 0) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White.copy(0.8f)),
                    ) {
                        item {
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Trending Series",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextButton(onClick = onClickViewMoreSeries) {
                                        Text("View More")
                                    }
                                }
                            }
                        }

                        items(listTrendingSeries, key = { it.cid ?: 0 }) { competition ->
                            TrendingSeriesItem(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                competition = competition,
                                onClickItem = onClickItemTrendingSeries,
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White.copy(0.8f)
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        item {
                            Column {
                                HorizontalPager(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    state = pagerStateTop,
                                    contentPadding = PaddingValues(10.dp)
                                ) { page: Int ->
                                    CricketMatchCard(
                                        modifier = Modifier.fillMaxSize(),
                                        item = listTop[page],
                                        onClickItem = onClickItemLive
                                    )
                                }
                                // Dot Indicator
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    repeat(listTop.size) {
                                        val color =
                                            if (pagerStateTop.currentPage == it) Color.White else Color.Gray

                                        val width by animateDpAsState(
                                            targetValue = if (pagerStateTop.currentPage == it) 12.dp else 8.dp,
                                            label = ""
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))

                                        Box(
                                            modifier = Modifier
                                                .size(width)
                                                .clip(CircleShape)
                                                .background(color),
                                        )
                                    }
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Top Stories",
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    TextButton(onClick = onClickViewMoreNews) {
                                        Text("View More")
                                    }
                                }
                            }
                        }

                        items(listDatum, key = { it.id ?: 0 }) { datum ->
                            TopSeriesItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(300.dp)
                                    .padding(horizontal = 10.dp, vertical = 8.dp),
                                storiesItem = datum,
                                onClickItem = onClickItemTopStories
                            )
                        }

                        items(news.itemCount) { index: Int ->
                            val sportNews = try {
                                news[index]
                            } catch (e: Throwable) {
                                null
                            }
                            sportNews?.let {
                                TopSeriesItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .padding(horizontal = 10.dp, vertical = 8.dp),
                                    storiesItem = it,
                                    onClickItem = onClickItemTopStories
                                )
                            }
                        }
                        when (news.loadState.append) {
                            is LoadState.Error -> {/* no-op */
                            }

                            LoadState.Loading -> {
                                item {
                                    LoadingItem(
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }

                            is LoadState.NotLoading -> {/* no-op */
                            }
                        }
                    }
                } else {
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
                                onClickItem = onClickItemLive
                            )
                        }
                    }
                }
            }
        }
    }

    AnimatedVisibility(
        visible = isLoading || news.loadState.refresh is LoadState.Loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadingData()
    }

    if (isFirsTime) {
        var checkedAgree by remember {
            mutableStateOf(false)
        }
        BasicAlertDialog(
            onDismissRequest = {},
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
        ) {
            val text = Constants.readPrivacyText(LocalContext.current)
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxHeight(0.8f),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Privacy Policy", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(
                    text = text, modifier = Modifier
                        .verticalScroll(
                            rememberScrollState()
                        )
                        .weight(1f),
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedAgree,
                        onCheckedChange = { checkedAgree = !checkedAgree })
                    Text(text = "I agreed to the Privacy Policy")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = onDismissFirstTime, enabled = checkedAgree) {
                        Text(text = "Accept")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun PreviewDialog() {
    Box(modifier = Modifier.fillMaxSize()) {
        if (true) {
            BasicAlertDialog(
                onDismissRequest = {},
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White)
            ) {
                val text = Constants.readPrivacyText(LocalContext.current)
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxHeight(0.8f),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Privacy Policy", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                    Text(
                        text = text, modifier = Modifier
                            .verticalScroll(
                                rememberScrollState()
                            )
                            .weight(1f),
                        color = Color.Black
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = false, onCheckedChange = {})
                        Text(text = "I agreed to the Privacy Policy")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { /*TODO*/ }, enabled = false) {
                            Text(text = "Accept")
                        }
                    }
                }
            }
        }
    }
}