package com.moingay.cricketsuper.presentation.ui.home.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.data.model.response.Category
import com.moingay.cricketsuper.data.model.response.Data
import com.moingay.cricketsuper.data.model.response.DataAttributes
import com.moingay.cricketsuper.data.model.response.Datum
import com.moingay.cricketsuper.data.model.response.DatumAttributes
import com.moingay.cricketsuper.data.model.response.Formats
import com.moingay.cricketsuper.data.model.response.Large
import com.moingay.cricketsuper.data.model.response.PostMedia
import com.moingay.cricketsuper.presentation.ui.components.ShimmerCricketSuper
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.utils.Constants

@Composable
fun TopSeriesItem(
    modifier: Modifier,
    storiesItem: Datum,
    onClickItem: (Datum) -> Unit,
    colors: CardColors = CardDefaults.cardColors()
) {
    var showShimmerEffect by remember { mutableStateOf(true) }
    val imageThumbnails =
        "${Constants.SportAPI.BASE_URL}${storiesItem.attributes?.postMedia?.data?.attributes?.formats?.thumbnail?.url}"
    val painter = rememberAsyncImagePainter(
        model = imageThumbnails,
        onLoading = { showShimmerEffect = true },
        onSuccess = { showShimmerEffect = false },
        onError = { showShimmerEffect = false }
    )
    Box(
        modifier = modifier,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = { onClickItem(storiesItem) },
            colors = colors
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    if (showShimmerEffect) {
                        ShimmerCricketSuper(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    if (showShimmerEffect) {
                        ShimmerCricketSuper(modifier = Modifier.height(20.dp))
                    } else {
                        Text(
                            text = storiesItem.attributes?.title ?: "",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    if (showShimmerEffect) {
                        ShimmerCricketSuper(modifier = Modifier.height(20.dp))
                    } else {
                        Text(
                            storiesItem.attributes?.contentSort ?: "",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                    if (showShimmerEffect) {
                        ShimmerCricketSuper(modifier = Modifier.height(20.dp))
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                "Read Full Article",
                                style = MaterialTheme.typography.bodyLarge,
                                color = BackgroundColorAppFirst
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_right),
                                contentDescription = null,
                                tint = BackgroundColorAppFirst
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewTopSeriesItem() {
    TopSeriesItem(
        modifier = Modifier.height(300.dp),
        storiesItem = Datum(
            id = 57,
            attributes = DatumAttributes(
                title = "IPL 2024 start with CSK and RCB match",
                slug = "post-53",
                contentSort = "IPL 2024 start with CSK and RCB match",
                contentFull = """
                <p style="margin-left:0px;">The opening match of IPL 2024 will see Chennai Super Kings, the defending champions, facing Royal Challengers Bangalore at the MA Chidambaram stadium in Chennai on March 22. Meanwhile, Gujarat Titans, the runners-up of the previous season, will kick off their campaign against Mumbai Indians, led by their former captain Hardik Pandya, in Ahmedabad on March 24.</p>
                <p style="margin-left:0px;">The BCCI has unveiled a preliminary schedule for the 2024 season, covering the first 17 days from March 22 to April 7, featuring 21 matches across 10 cities, including four double-headers.</p>
                <p style="margin-left:0px;">Punjab Kings and Delhi Capitals will compete in the season's inaugural afternoon fixture on March 23 in Mohali. Later the same day, Kolkata Knight Riders will commence their season at home against Sunrisers Hyderabad. Rajasthan Royals will also start their season at home, taking on Lucknow Super Giants on the first Sunday of the season.</p>
                <p style="margin-left:0px;">During this initial phase, there will be no matches scheduled in Delhi, with the Delhi Capitals hosting their two home games in Vizag. The BCCI is expected to finalize the remaining schedule following the announcement of General Election dates by the Election Commission of India.</p>
            """.trimIndent(),
                status = true,
                createdAt = "2024-02-25T10:56:51.511Z",
                updatedAt = "2024-02-25T10:56:52.591Z",
                publishedAt = "2024-02-25T10:56:52.584Z",
                seo = null,
                postMedia = PostMedia(
                    data = Data(
                        id = 55,
                        attributes = DataAttributes(
                            name = "111.jpg",
                            alternativeText = null,
                            caption = null,
                            width = 526,
                            height = 349,
                            formats = Formats(
                                thumbnail = Large(
                                    name = "thumbnail_111.jpg",
                                    hash = "thumbnail_111_2bd83b2b9d",
                                    ext = ".jpg",
                                    mime = "image/jpeg",
                                    path = null,
                                    width = 235,
                                    height = 156,
                                    size = 10.09,
                                    url = "/uploads/thumbnail_111_2bd83b2b9d.jpg"
                                ),
                                small = Large(
                                    name = "small_111.jpg",
                                    hash = "small_111_2bd83b2b9d",
                                    ext = ".jpg",
                                    mime = "image/jpeg",
                                    path = null,
                                    width = 500,
                                    height = 332,
                                    size = 31.79,
                                    url = "/uploads/small_111_2bd83b2b9d.jpg"
                                )
                            ),
                            hash = "111_2bd83b2b9d",
                            ext = ".jpg",
                            mime = "image/jpeg",
                            size = 34.48,
                            url = "/uploads/111_2bd83b2b9d.jpg",
                            previewUrl = null,
                            provider = "local",
                            providerMetadata = null,
                            createdAt = "2024-02-25T10:56:43.759Z",
                            updatedAt = "2024-02-25T10:56:43.759Z"
                        )
                    )
                ),
                category = Category(id = 57)
            )
        ),
        onClickItem = {

        }
    )
}

