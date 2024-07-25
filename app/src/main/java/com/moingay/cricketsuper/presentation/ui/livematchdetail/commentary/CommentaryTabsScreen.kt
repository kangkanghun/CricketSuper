package com.moingay.cricketsuper.presentation.ui.livematchdetail.commentary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moingay.cricketsuper.data.model.response.Commentary
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundHeaderLive
import com.moingay.cricketsuper.utils.Constants

@Composable
fun CommentaryTabsScreen(
    modifier: Modifier = Modifier,
    listCommentary: List<Commentary> = emptyList()
){
    val list by remember {
        mutableStateOf(listCommentary.reversed())
    }
    Box(modifier = modifier){
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(list) { commentary ->
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