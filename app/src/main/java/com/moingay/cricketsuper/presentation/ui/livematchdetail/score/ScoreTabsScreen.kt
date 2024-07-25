package com.moingay.cricketsuper.presentation.ui.livematchdetail.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.presentation.ui.livematchdetail.score.components.ItemCardScore

@Composable
fun ScoreTabsScreen(
    modifier: Modifier = Modifier,
    scoreCard: ScoreMatchResponse,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ItemCardScore(
            modifier = Modifier.fillMaxWidth(),
            teamName = scoreCard.teamA?.name ?: "",
            teamLogo = scoreCard.teamA?.logoUrl ?: "",
            score = scoreCard.teamA?.scoresFull ?: "",
        )
        ItemCardScore(
            modifier = Modifier.fillMaxWidth(),
            teamName = scoreCard.teamB?.name ?: "",
            teamLogo = scoreCard.teamB?.logoUrl ?: "",
            score = scoreCard.teamB?.scoresFull ?: "",
        )
    }
}