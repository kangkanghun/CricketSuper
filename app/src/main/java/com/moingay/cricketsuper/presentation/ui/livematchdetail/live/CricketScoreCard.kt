package com.moingay.cricketsuper.presentation.ui.livematchdetail.live

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moingay.cricketsuper.data.model.response.Bowler
import com.moingay.cricketsuper.data.model.response.InningBatsman

// Reusable Score Card Composable
@Composable
fun BatsmanCard(
    modifier: Modifier = Modifier,
    title: String,
    listBatsman: List<InningBatsman>,
    partnershipText: String,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE9ECEF)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            // Table Header
            BatsmanScoreTableHeader(Color(0xFF455D75), title = title)
            Spacer(modifier = Modifier.height(10.dp))
            // Player Data Rows
            listBatsman.forEach { inningBatsman ->
                BatsmanScoreTableRow(inningBatsman, Color.Black)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Partnership or Last Wicket Information
            Text(
                text = partnershipText,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BowlerCard(
    modifier: Modifier = Modifier,
    title: String,
    listBowler: List<Bowler>,
    partnershipText: String,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE9ECEF)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            // Table Header
            BowlerScoreTableHeader(Color(0xFF455D75), title = title)
            Spacer(modifier = Modifier.height(10.dp))
            // Player Data Rows
            listBowler.forEach { bowler ->
                BowlerScoreTableRow(bowler, Color.Black)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Partnership or Last Wicket Information
            Text(
                text = partnershipText,
                color = Color.Black,
                fontSize = 14.sp
            )
        }
    }
}

// Table Header Row Composable
@Composable
fun BatsmanScoreTableHeader(primaryTextColor: Color, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = primaryTextColor
        )
        Row(
            modifier = Modifier
                .weight(2f),
            horizontalArrangement = Arrangement.SpaceBetween // Evenly space header items
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "R",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "B",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "4s",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "6s",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "SR",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
// Table Header Row Composable
@Composable
fun BowlerScoreTableHeader(primaryTextColor: Color, title: String) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = primaryTextColor
        )
        Row(
            modifier = Modifier
                .weight(2f),
            horizontalArrangement = Arrangement.SpaceBetween // Evenly space header items
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "R",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "B",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "4s",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "6s",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = "SR",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun BatsmanScoreTableRow(inningBatsman: InningBatsman, primaryTextColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = inningBatsman.name ?: "",
            color = primaryTextColor
        )
        Row(
            modifier = Modifier
                .weight(2f)
                .padding(vertical = 4.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = inningBatsman.runs ?: "",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = inningBatsman.ballsFaced ?: "",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            ) // Format for bowlers
            Text(
                modifier = Modifier.weight(1f),
                text = inningBatsman.fours ?: "",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = inningBatsman.sixes ?: "",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = inningBatsman.strikeRate ?: "",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun BowlerScoreTableRow(bowler: Bowler, primaryTextColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = bowler.name ?: "",
            color = primaryTextColor
        )
        Row(
            modifier = Modifier
                .weight(2f)
                .padding(vertical = 4.dp),
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = bowler.overs ?: "",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = (bowler.maidens ?: "").toString(),
                color = primaryTextColor,
                textAlign = TextAlign.Center
            ) // Format for bowlers
            Text(
                modifier = Modifier.weight(1f),
                text = (bowler.runsConceded ?: "").toString(),
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = (bowler.wickets ?: "").toString(),
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier.weight(1f),
                text = bowler.econ ?: "",
                color = primaryTextColor,
                textAlign = TextAlign.Center
            )
        }
    }
}