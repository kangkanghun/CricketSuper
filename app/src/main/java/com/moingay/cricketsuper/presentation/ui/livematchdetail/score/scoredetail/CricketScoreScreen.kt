package com.moingay.cricketsuper.presentation.ui.livematchdetail.score.scoredetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond

@Composable
fun CricketScoreScreen(navController: NavHostController) {
    // TODO UI Rendering
    CricketScoreScreen(
        modifier = Modifier.fillMaxSize(),
        onBack = { navController.navigateUp() },
        scoreCard = ScoreMatchResponse()
    )
}

@Composable
fun CricketScoreScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {/* no-op */ },
    scoreCard: ScoreMatchResponse,
) {
    Column(modifier = modifier) {
        Row(
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
                .zIndex(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = scoreCard.shortTitle ?: "",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
        ScoreCard(
            header = listOf("Batsman", "R", "B", "4s", "6s", "SR"),
            data = listOf(
                BatsmanData(
                    "Ni Putu Ayu Nanda\nSakarini",
                    "25",
                    "30",
                    "2",
                    "0",
                    "83.33",
                    "Not out"
                ),
                BatsmanData(
                    "Wesikaratna Dewi\nc V Kumar b J Pooranakaran",
                    "18",
                    "21",
                    "2",
                    "0",
                    "85.71"
                ),
                BatsmanData("Fitria Rada Rani", "2", "10", "0", "0", "20.00", "Not out")
            ),
            extras = "Extras\t\tT6\tb0\t\tlb0\tw4\tnb2\tp0",
            total = "Total\t\t\t\t\t\t\t\t\t\t\t51/1 (9.5 ov)"
        )

        Spacer(modifier = Modifier.height(16.dp))

        ScoreCard(
            header = listOf("Bowler", "O", "M", "R", "W", "ER"),
            data = listOf(
                BowlerData("Ada Bhasin", "2", "0", "6", "0", "3.00"),
                BowlerData("Shafina Mahesh", "2", "0", "8", "0", "4.00"),
                BowlerData("Johanna\nPooranakaran", "1", "0", "9", "0", "9.00"),
                BowlerData("GK Diviya", "2.5", "0", "15", "0", "5.29"),
                BowlerData("Jocelyn\nPooranakaran", "2", "0", "13", "1", "6.50")
            )
        )
    }
}


@Composable
fun ScoreCard(
    header: List<String>,
    data: List<ScoreData>,
    extras: String? = null, // Add extras parameter
    total: String? = null // Add total parameter
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(4.dp)
            ) {
                header.forEach { item ->
                    Text(
                        text = item,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentSize(Alignment.Center)
                    )
                }
            }

            // Data Rows
            data.forEach { scoreData ->
                ScoreDataRow(scoreData)
                Spacer(modifier = Modifier.height(4.dp))
            }

            // Extras (if available)
            if (extras != null) {
                Text(
                    text = extras,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                )
            }

            // Total (if available)
            if (total != null) {
                Text(
                    text = total,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ScoreDataRow(scoreData: ScoreData) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = scoreData.playerName,
            modifier = Modifier
                .weight(2f)
                .wrapContentSize(Alignment.CenterStart)
        )
        scoreData.stats.forEach { stat ->
            Text(
                text = stat,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

interface ScoreData {
    val playerName: String
    val stats: List<String>
}

data class BatsmanData(
    override val playerName: String,
    val runs: String,
    val balls: String,
    val fours: String,
    val sixes: String,
    val strikeRate: String,
    val outStatus: String? = null // Add outStatus property
) : ScoreData {
    override val stats: List<String>
        get() = listOf(runs, balls, fours, sixes, strikeRate)
}

data class BowlerData(
    override val playerName: String,
    val overs: String,
    val maidens: String,
    val runsConceded: String,
    val wickets: String,
    val economyRate: String
) : ScoreData {
    override val stats: List<String>
        get() = listOf(overs, maidens, runsConceded, wickets, economyRate)
}


@Preview
@Composable
fun CricketScoreScreenPreview() {
    MaterialTheme {
        CricketScoreScreen(
            modifier = Modifier.fillMaxSize(),
            onBack = {/* no-op */},
            scoreCard = ScoreMatchResponse()
        )
    }
}