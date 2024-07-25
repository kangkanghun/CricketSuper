package com.moingay.cricketsuper.presentation.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.data.model.response.Team
import com.moingay.cricketsuper.data.model.response.Toss
import com.moingay.cricketsuper.data.model.response.Venue
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import com.moingay.cricketsuper.presentation.ui.theme.CricketSuperTheme

@Composable
fun CricketMatchCard(
    modifier: Modifier = Modifier,
    item: Item,
    onClickItem: (Item) -> Unit = {/* no-op */ }
) {
    val statusBackgroundColor = when (item.statusStr) {
        MatchStatus.COMPLETED -> Color(0xFF64B5F6)
        MatchStatus.LIVE -> Color(0xFFCA1515)
        MatchStatus.SCHEDULED -> Color(0xFFF8AE58)
        MatchStatus.ABANDONED -> Color.Gray
        null -> Color(0xFF64B5F6)
    }
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        modifier = modifier.padding(5.dp),
        onClick = { onClickItem(item) }
    ) {
        Column(
            modifier = modifier.background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        BackgroundColorAppSecond,
                        BackgroundColorAppFirst
                    )
                )
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Match Type and Status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.formatStr ?: "",
                    color = Color.White,
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 5.dp))
                        .background(Color.Green.copy(0.4f))
                        .padding(5.dp)
                )
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 5.dp))
                        .background(statusBackgroundColor)
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_circle_live),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(15.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = item.statusStr?.name ?: "",
                        color = Color.White,
                    )
                }
            }

            Text(
                text = item.title ?: "",
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            Text(
                text = item.venue?.location ?: "",
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 15.dp),
                fontWeight = FontWeight.Bold
            )

            // Match Details
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(1f),
                        painter = rememberAsyncImagePainter(model = item.teamA?.logoUrl),
                        contentDescription = null
                    )
                    Text(
                        text = item.teamA?.name ?: "",
                        fontSize = 14.sp,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(text = item.teamA?.scores ?: "", fontSize = 20.sp, color = Color.White)
                    Text(
                        text = stringResource(
                            id = R.string.label_overs,
                            item.teamA?.overs ?: ""
                        ), fontSize = 14.sp, color = Color.White
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_trade),
                    contentDescription = null,
                    tint = Color.White
                )
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = item.teamB?.scores ?: "", fontSize = 20.sp, color = Color.White)
                    Text(
                        text = stringResource(
                            id = R.string.label_overs,
                            item.teamB?.overs ?: ""
                        ), fontSize = 14.sp, color = Color.White
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .aspectRatio(1f),
                        painter = rememberAsyncImagePainter(model = item.teamB?.logoUrl),
                        contentDescription = null
                    )
                    Text(
                        text = item.teamB?.name ?: "",
                        fontSize = 14.sp,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Match Result
            Text(
                modifier = Modifier.padding(10.dp),
                text = item.statusNote ?: "",
                fontSize = 14.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun CricketMatchCardLive(
    modifier: Modifier,
    item: Item,
    onClickItem: (Item) -> Unit = {/* no-op */ },
) {
    Box(modifier = modifier) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            onClick = { onClickItem(item) }
        ) {
            Column(
                modifier = Modifier.background(Color.White.copy(0.8f)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                // Match Type and Status
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = item.formatStr ?: "",
                        color = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomEnd = 5.dp))
                            .background(Color.DarkGray)
                            .padding(5.dp)
                    )
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomStart = 5.dp))
                            .background(Color.DarkGray)
                            .padding(horizontal = 10.dp, vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = item.shortTitle ?: "",
                            color = Color.White,
                        )
                    }
                }

                Text(
                    text = item.title ?: "",
                    color = BackgroundColorAppFirst,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
                Text(
                    text = item.venue?.location ?: "",
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 15.dp),
                    fontWeight = FontWeight.Bold
                )

                // Match Details
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .aspectRatio(1f),
                            painter = rememberAsyncImagePainter(model = item.teamA?.logoUrl),
                            contentDescription = null
                        )
                        Text(
                            text = item.teamA?.name ?: "",
                            fontSize = 14.sp,
                            color = Color.Black,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(text = item.teamA?.scores ?: "", fontSize = 20.sp, color = Color.Black)
                        Text(
                            text = stringResource(
                                id = R.string.label_overs,
                                item.teamA?.overs ?: ""
                            ), fontSize = 14.sp, color = Color.Black
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_trade),
                        contentDescription = null,
                        tint = BackgroundColorAppFirst
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = item.teamB?.scores ?: "", fontSize = 20.sp, color = Color.Black)
                        Text(
                            text = stringResource(
                                id = R.string.label_overs,
                                item.teamB?.overs ?: ""
                            ), fontSize = 14.sp, color = Color.Black
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .aspectRatio(1f),
                            painter = rememberAsyncImagePainter(model = item.teamB?.logoUrl),
                            contentDescription = null
                        )
                        Text(
                            text = item.teamB?.name ?: "",
                            fontSize = 14.sp,
                            color = Color.Black,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Match Result
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = item.statusNote ?: "",
                    fontSize = 14.sp,
                    color = BackgroundColorAppFirst,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
@Preview
fun MatchCardPreview() {
    CricketSuperTheme {
        CricketMatchCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            item = item
        )
    }
}

val item = Item(
    matchId = 77827,
    title = "Shrachi Rarh Tigers Womens vs Lux Shyam Kolkata Tigers Womens",
    shortTitle = "SRT-W vs LSKT-W",
    subtitle = "2nd Semi Final",
    matchNumber = "30",
    format = 8,
    formatStr = "Women T20",
    status = 3,
    statusStr = MatchStatus.LIVE,
    statusNote = "Innings Break : Shrachi Rarh Tigers Womens elected to bowl",
    verified = "false",
    preSquad = "true",
    oddsAvailable = "false",
    gameState = 6,
    gameStateStr = "Inning Break",
    domestic = "0",
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
    teamA = Team(
        teamId = 128525,
        name = "Shrachi Rarh Tigers Womens",
        shortName = "SRT-W",
        logoUrl = "https://images.entitysport.com/assets/uploads/2024/06/SRTW.png",
        scoresFull = "94/2 (10 ov)",
        scores = "94/2",
        overs = "10"
    ),
    teamB = Team(
        teamId = 128521,
        name = "Lux Shyam Kolkata Tigers Womens",
        shortName = "LSKT-W",
        logoUrl = "https://images.entitysport.com/assets/uploads/2024/06/LSKTW.png",
        scoresFull = "94/2 (10 ov)",
        scores = "94/2",
        overs = "10"
    ),
    dateStart = "2024-06-27 08:00:00",
    dateEnd = "2024-06-27 18:00:00",
    timestampStart = 1719475200,
    timestampEnd = 1719511200,
    dateStartIst = "2024-06-27 13:30:00",
    dateEndIst = "2024-06-27 23:30:00",
    venue = Venue(
        venueId = "482",
        name = "Jadavpur University Campus",
        location = "Kolkata",
        country = "India",
        timezone = ""
    ),
    umpires = "Soham Chowdhury (India), Binita Roymoulick (India), Siddhartha Jati (India, TV)",
    referee = "Kathakali Asok Banerjee (India)",
    equation = "",
    live = "Innings Break : Shrachi Rarh Tigers Womens elected to bowl",
    result = "",
    resultType = "",
    winMargin = "",
    winningTeamId = 0,
    commentary = 1,
    wagon = 0,
    latestInningNumber = 2,
    preSquadTime = "2024-06-25 18:39:33",
    verifyTime = "",
    matchDlsAffected = "false",
    day = "0",
    session = "0",
    toss = Toss(
        text = "Shrachi Rarh Tigers Womens elected to bowl",
        winner = 128525,
        decision = 2
    )
)