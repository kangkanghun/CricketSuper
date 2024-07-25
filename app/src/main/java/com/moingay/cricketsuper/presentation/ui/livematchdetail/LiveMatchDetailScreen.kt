package com.moingay.cricketsuper.presentation.ui.livematchdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.data.model.response.Bowler
import com.moingay.cricketsuper.data.model.response.InningBatsman
import com.moingay.cricketsuper.data.model.response.LiveMatch
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.domain.navigation.tabslivedetail.Tabs
import com.moingay.cricketsuper.presentation.ui.livematchdetail.commentary.CommentaryTabsScreen
import com.moingay.cricketsuper.presentation.ui.livematchdetail.info.InfoTabsScreen
import com.moingay.cricketsuper.presentation.ui.livematchdetail.live.LiveTabsScreen
import com.moingay.cricketsuper.presentation.ui.livematchdetail.score.ScoreTabsScreen
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt


@Composable
fun LiveMatchDetailScreen(navController: NavHostController) {
    // TODO UI Rendering
    val liveMatchDetailViewModel: LiveMatchDetailViewModel = koinViewModel()
    val uiState by liveMatchDetailViewModel.stateFlow.collectAsState()

    LiveMatchDetailScreen(
        modifier = Modifier.fillMaxSize(),
        scoreCard = uiState.scoreMatchResponse?.response ?: ScoreMatchResponse(),
        liveMatch = uiState.matchDetailResponse?.response ?: LiveMatch(),
        onBack = { navController.navigateUp() },
        listBatsmanLive = uiState.listBatsmanLive,
        listBowlersLive = uiState.listBowlersLive
    )
}

@Composable
fun LiveMatchDetailScreen(
    modifier: Modifier = Modifier,
    scoreCard: ScoreMatchResponse,
    liveMatch: LiveMatch,
    onBack: () -> Unit = {/* no-op */ },
    listBatsmanLive: List<InningBatsman> = emptyList(),
    listBowlersLive: List<Bowler> = emptyList(),
) {
    val tabs = listOf(
        Tabs.Live,
        Tabs.Score,
        Tabs.Commentary,
        Tabs.Info
    )
    var collapsingTopHeight by remember { mutableFloatStateOf(0f) }

    var offset by remember { mutableFloatStateOf(0f) }

    fun calculateOffset(delta: Float): Offset {
        val oldOffset = offset
        val newOffset = (oldOffset + delta).coerceIn(-collapsingTopHeight, 0f)
        offset = newOffset
        return Offset(0f, newOffset - oldOffset)
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset =
                when {
                    available.y >= 0 -> Offset.Zero
                    offset == -collapsingTopHeight -> Offset.Zero
                    else -> calculateOffset(available.y)
                }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset =
                when {
                    available.y <= 0 -> Offset.Zero
                    offset == 0f -> Offset.Zero
                    else -> calculateOffset(available.y)
                }
        }
    }
    val coroutineScope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = { tabs.size },
        initialPage = 0,
    )

    val statusBackgroundColor = when (scoreCard.statusStr) {
        MatchStatus.COMPLETED -> Color(0xFF64B5F6)
        MatchStatus.LIVE -> Color(0xFFCA1515)
        MatchStatus.SCHEDULED -> Color(0xFFF8AE58)
        MatchStatus.ABANDONED -> Color.Gray
        null -> Color(0xFF64B5F6)
    }


    Column(modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            BackgroundColorAppFirst,
                            BackgroundColorAppSecond
                        )
                    )
                )
                .padding(5.dp)
                .zIndex(1f)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = scoreCard.shortTitle ?: "",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = scoreCard.statusStr?.name ?: "",
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(statusBackgroundColor)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .nestedScroll(nestedScrollConnection),
        ) {
            Box(
                modifier = Modifier
                    .onSizeChanged { size ->
                        collapsingTopHeight = size.height.toFloat()
                    }
                    .offset { IntOffset(x = 0, y = offset.roundToInt()) },
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${scoreCard.formatStr} | ${scoreCard.subtitle} | ${scoreCard.title}",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${scoreCard.venue?.name} ${scoreCard.venue?.location}",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Match Scorecard Section
                    Surface(
                        color = Color(0xFF1B0033), // Dark background for the scorecard
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                BorderStroke(2.dp, Color.White),
                                shape = RoundedCornerShape(10.dp)
                            ),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            TeamScoreRow(
                                teamName = scoreCard.teamA?.name ?: "",
                                teamLogo = scoreCard.teamA?.logoUrl ?: "",
                                score = scoreCard.teamA?.scoresFull ?: "",
                                scoreIcon = R.drawable.ic_cricket_ball
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            TeamScoreRow(
                                teamName = scoreCard.teamB?.name ?: "",
                                teamLogo = scoreCard.teamB?.logoUrl ?: "",
                                score = scoreCard.teamB?.scoresFull ?: "",
                                scoreIcon = R.drawable.ic_cricket_live_detail
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = scoreCard.statusNote ?: "",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 16.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Match Stats Section
                            MatchStatRow(
                                label = "Target",
                                value = liveMatch.liveInning?.target ?: ""
                            )
                            HorizontalDivider(color = Color.DarkGray)
                            MatchStatRow(
                                label = "Current Run Rate",
                                value = (liveMatch.liveScore?.runRate ?: "").toString()
                            )
                            HorizontalDivider(color = Color.DarkGray)
                            MatchStatRow(label = "Required Run Rate", value = "")
                            HorizontalDivider(color = Color.DarkGray)
                            MatchStatRow(
                                label = "Last 5 Overs",
                                value = liveMatch.liveInning?.lastFiveOvers ?: ""
                            )
                            HorizontalDivider(color = Color.DarkGray)
                            MatchStatRow(
                                label = "Last 10 Overs",
                                value = liveMatch.liveInning?.lastTenOvers ?: ""
                            )
                            HorizontalDivider(color = Color.DarkGray)
                        }
                    }
                }
            }
            Box(
                modifier = Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = (collapsingTopHeight + offset).roundToInt()
                    )
                },
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // TabRow with tabs
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
                        tabs.forEachIndexed { index, tabs ->
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
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        state = pagerState
                    ) { indexPager ->
                        when {
                            tabs[indexPager] == Tabs.Live -> {
                                LiveTabsScreen(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    liveMatch = liveMatch,
                                    scoreCard = scoreCard,
                                    listBatsmanLive = listBatsmanLive,
                                    listBowlersLive = listBowlersLive
                                )
                            }

                            tabs[indexPager] == Tabs.Score -> {
                                ScoreTabsScreen(
                                    scoreCard = scoreCard,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp)
                                )
                            }

                            tabs[indexPager] == Tabs.Commentary -> {
                                CommentaryTabsScreen(
                                    modifier = Modifier.fillMaxWidth(),
                                    listCommentary = liveMatch.commentaries ?: emptyList()
                                )
                            }

                            tabs[indexPager] == Tabs.Info -> {
                                InfoTabsScreen(
                                    modifier = Modifier.fillMaxSize().padding(10.dp),
                                    scoreCard = scoreCard
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TeamScoreRow(
    teamName: String,
    teamLogo: String,
    score: String,
    scoreIcon: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = teamLogo),
                contentDescription = "Team Logo",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = teamName,
                color = Color.White
            )
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                painter = painterResource(id = scoreIcon),
                contentDescription = "Score Icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = score,
                color = Color.White,
            )
        }
    }
}

@Composable
fun MatchStatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color(0xFFAAAAAA),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            color = Color.White
        )
    }
}

@Composable
@Preview(name = "LiveMatchDetail", showSystemUi = true)
private fun LiveMatchDetailScreenPreview() {
    LiveMatchDetailScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        BackgroundColorAppFirst,
                        BackgroundColorAppSecond
                    )
                )
            ),
        scoreCard = ScoreMatchResponse(),
        liveMatch = LiveMatch()
    )
}
