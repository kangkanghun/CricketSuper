package com.moingay.cricketsuper.presentation.ui.livematchdetail.live

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moingay.cricketsuper.data.model.response.Bowler
import com.moingay.cricketsuper.data.model.response.InningBatsman
import com.moingay.cricketsuper.data.model.response.LiveMatch
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundHeaderLive
import com.moingay.cricketsuper.utils.Constants

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LiveTabsScreen(
    modifier: Modifier = Modifier,
    scoreCard: ScoreMatchResponse,
    listBatsmanLive: List<InningBatsman> = emptyList(),
    listBowlersLive: List<Bowler> = emptyList(),
    liveMatch: LiveMatch,
){
    val string = liveMatch.liveInning?.recentScores ?: ""
    val listRecent by remember(string) { mutableStateOf(string.split(",")) }
    val listLiveComment by remember(liveMatch.commentaries) {
        mutableStateOf(liveMatch.commentaries?.reversed()?.take(20))
    }
    Box(modifier = modifier){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                BatsmanCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    title = "Batsman",
                    listBatsman = listBatsmanLive,
                    partnershipText = "Current Partnership: ${scoreCard.innings?.lastOrNull()?.currentPartnership?.runs}/${scoreCard.innings?.lastOrNull()?.currentPartnership?.balls}",
                )
            }

            item {
                val lastWicket = scoreCard.innings?.lastOrNull()?.lastWicket
                BowlerCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    title = "Bowler",
                    listBowler = listBowlersLive,
                    partnershipText = "Last Wicket: ${lastWicket?.name} ${lastWicket?.runs} (${lastWicket?.balls})"
                )
            }

            if (listRecent.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Column {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Blue.copy(0.5f))
                                    .padding(10.dp),
                                text = "Recent",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            FlowRow(
                                modifier = Modifier.padding(10.dp),
                                horizontalArrangement = Arrangement.spacedBy(
                                    5.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                repeat(listRecent.size) { index ->
                                    val text = listRecent[index]
                                    Box(
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Constants.getBackgroundColorScore(
                                                    text
                                                )
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = text,
                                            fontSize = 20.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth()
                        .clip(
                            RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp
                            )
                        )
                        .background(
                            BackgroundHeaderLive
                        )
                        .padding(5.dp),
                    text = "Live Commentary",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            items(listLiveComment ?: emptyList()) { commentary ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (commentary.event == "overend") {
                        Column(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 10.dp,
                                        bottomEnd = 10.dp
                                    )
                                )
                                .background(BackgroundHeaderLive)
                                .padding(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            Text(
                                text = "End of over",
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            Text(
                                text = commentary.over ?: "",
                                fontSize = 25.sp,
                                color = Color.White
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 40.dp,
                                        bottomEnd = 40.dp
                                    )
                                )
                                .background(BackgroundHeaderLive)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${commentary.over}.${commentary.ball}",
                                fontSize = 20.sp,
                                color = Color.White
                            )
                            commentary.score?.let {
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Constants.getBackgroundColorScore(
                                                it
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = it,
                                        fontSize = 20.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = commentary.commentary ?: "",
                        modifier = Modifier.weight(1f),
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
                if (commentary.event == "overend") {
                    Spacer(modifier = Modifier.size(10.dp))
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview
@Composable
fun LiveTabsScreenPreview() {
    LiveTabsScreen(
        modifier = Modifier.fillMaxSize(),
        scoreCard = ScoreMatchResponse(),
        liveMatch = LiveMatch(),
        listBatsmanLive = emptyList(),
        listBowlersLive = emptyList()
    )
}