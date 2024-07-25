package com.moingay.cricketsuper.presentation.ui.livematchdetail.score.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.moingay.cricketsuper.R

@Composable
fun ItemCardScore(
    modifier: Modifier = Modifier,
    teamName: String,
    teamLogo: String,
    score: String,
) {
    Box(modifier = modifier) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = teamLogo),
                        contentDescription = "Team Logo",
                        modifier = Modifier.size(40.dp)
                    )
                    Text(
                        text = teamName,
                        color = Color.Black
                    )
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = score,
                        color = Color.Black,
                    )
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
}

@Preview
@Composable
private fun PreviewItemCardScore() {
    ItemCardScore(modifier = Modifier.fillMaxWidth(), teamName = "AAAAA", teamLogo = "", score = "234523453245")
}