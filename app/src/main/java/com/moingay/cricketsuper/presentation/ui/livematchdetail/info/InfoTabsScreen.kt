package com.moingay.cricketsuper.presentation.ui.livematchdetail.info

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundHeaderLive
import com.moingay.cricketsuper.utils.Constants

@Composable
fun InfoTabsScreen(
    modifier: Modifier = Modifier,
    scoreCard: ScoreMatchResponse = ScoreMatchResponse(),
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            // Top Bar (Teams and Arrow)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = rememberAsyncImagePainter(model = scoreCard.teamA?.logoUrl),
                            contentDescription = "Team Logo",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(scoreCard.teamA?.shortName ?: "", color = Color.White, fontSize = 18.sp)
                    }

                    Icon(
                        painter = painterResource(id = R.drawable.ic_trade), // Replace with actual arrow icon
                        contentDescription = "Match Arrow",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(scoreCard.teamB?.shortName ?: "", color = Color.White, fontSize = 18.sp)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Image(
                            painter = rememberAsyncImagePainter(model = scoreCard.teamB?.logoUrl),
                            contentDescription = "Team Logo",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            item {
                // Strategic Timeout Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BackgroundHeaderLive),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = scoreCard.live ?: "",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            item {
                // Match Information Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        MatchInfoItem("Series:", scoreCard.competition?.title ?: "")
                        MatchInfoItem("Match:", scoreCard.shortTitle ?: "")
                        scoreCard.dateStartIst?.let {
                            val date = Constants.formatRegexTimeToRegex(
                                "yyyy-MM-dd HH:mm:ss",
                                it,
                                "yyyy-MM-dd"
                            )
                            val time = Constants.formatRegexTimeToRegex(
                                "yyyy-MM-dd HH:mm:ss",
                                it,
                                "HH:mm:ss"
                            )
                            MatchInfoItem("Date:", date)
                            MatchInfoItem("Time:", time)
                        }
                        MatchInfoItem(
                            "Venue:",
                            "${scoreCard.venue?.name}, ${scoreCard.venue?.location}"
                        )
                        MatchInfoItem(
                            "Umpires:",
                            scoreCard.umpires ?: ""
                        )
                        MatchInfoItem("Match Referee:", scoreCard.referee ?: "")
                    }
                }
            }
        }
    }
}

@Composable
fun MatchInfoItem(label: String, value: String) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            Text(label, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
        Text(modifier = Modifier.weight(2f), text = value, color = Color.Black, fontSize = 14.sp)
    }
}

@Preview
@Composable
private fun PreviewInfoTabsScreen() {
    InfoTabsScreen(
        modifier = Modifier.fillMaxSize()
    )
}