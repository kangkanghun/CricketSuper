package com.moingay.cricketsuper.presentation.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.utils.Constants

@Composable
fun TrendingSeriesItem(
    modifier: Modifier,
    competition: Competition,
    onClickItem: (Competition) -> Unit,
    colors: CardColors = CardDefaults.cardColors()
) {
    Box(
        modifier = modifier,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = { onClickItem(competition) },
            colors = colors
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        competition.title ?: "",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${competition.totalMatches} Matches, ${
                            Constants.formatRegexTimeToRegex(
                                "yyyy-MM-dd",
                                competition.dateStart,
                                "d MMM"
                            )
                        } - ${
                            Constants.formatRegexTimeToRegex(
                                "yyyy-MM-dd",
                                competition.dateEnd,
                                "d MMM, yyyy"
                            )
                        }",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "View Details",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(50.dp),
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTrendingSeriesItem() {
    TrendingSeriesItem(
        modifier = Modifier,
        competition = Competition(
            cid = 128684,
            title = "Bengal Women's Pro T20 League",
            abbr = "Bengal Women's T20",
            type = "tournament",
            category = "women",
            matchFormat = "woment20",
            season = "2024",
            status = "live",
            dateStart = "2024-06-12",
            dateEnd = "2024-06-28",
            country = "in",
            totalMatches = "31",
            totalRounds = "1",
            totalTeams = "10"
        ),
        onClickItem = {

        }
    )
}