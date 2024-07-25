package com.moingay.cricketsuper.presentation.ui.news.newsdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.moingay.cricketsuper.data.model.response.Category
import com.moingay.cricketsuper.data.model.response.Data
import com.moingay.cricketsuper.data.model.response.DataAttributes
import com.moingay.cricketsuper.data.model.response.Datum
import com.moingay.cricketsuper.data.model.response.DatumAttributes
import com.moingay.cricketsuper.data.model.response.Formats
import com.moingay.cricketsuper.data.model.response.Large
import com.moingay.cricketsuper.data.model.response.PostMedia
import com.moingay.cricketsuper.presentation.ui.components.ShimmerCricketSuper
import com.moingay.cricketsuper.presentation.ui.components.ShimmerItem
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import com.moingay.cricketsuper.utils.Constants
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsDetailScreen(navController: NavHostController) {
    // TODO UI Rendering

    val newsDetailViewModel: NewsDetailViewModel = koinViewModel()
    val uiState by newsDetailViewModel.stateFlow.collectAsState()

    NewsDetailScreen(
        modifier = Modifier.fillMaxSize(),
        postDetail = uiState.sportStoriesResponse?.data?.attributes ?: DatumAttributes(),
        onBack = { navController.navigateUp() }
    )
}

@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    postDetail: DatumAttributes,
    onBack: () -> Unit = {/* no-op */ }
) {
    var showShimmer by remember {
        mutableStateOf(true)
    }
    val painter =
        rememberAsyncImagePainter(
            model = "${Constants.SportAPI.BASE_URL}${postDetail.postMedia?.data?.attributes?.url}",
            onSuccess = { showShimmer = false },
            onError = { showShimmer = false },
            onLoading = { showShimmer = true }
        )
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
                text = postDetail.title ?: "",
                fontSize = 25.sp,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White.copy(0.8f)),
            contentPadding = PaddingValues(15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Column {
                    Text(
                        text = postDetail.title ?: "",
                        fontSize = 25.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = "Posted by author",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                    Text(
                        text = "Posted: ${
                            Constants.formatRegexTimeToRegex(
                                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                                postDetail.publishedAt ?: "",
                                "dd MMM yyyy"
                            )
                        }",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal,
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = postDetail.contentSort ?: "",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                    if (showShimmer) {
                        ShimmerCricketSuper(
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
            item {
                val styledAnnotatedString =
                    AnnotatedString.fromHtml(htmlString = postDetail.contentFull ?: "")
                Text(
                    text = styledAnnotatedString,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(top = 10.dp),
                    letterSpacing = (0.5).sp,
                )
            }
        }
    }
}

@Composable
@Preview(name = "NewsDetail", showSystemUi = true)
private fun NewsDetailScreenPreview() {
    NewsDetailScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        BackgroundColorAppFirst,
                        BackgroundColorAppSecond
                    )
                )
            ),
        postDetail = DatumAttributes(
            title = "It's got to be the greatest ODI innings: Cummins",
            slug = "post-52",
            contentSort = "It's got to be the greatest ODI innings: Cummins",
            contentFull = """
                <p>Was that the most exceptional ODI innings ever witnessed? Australia's captain, Pat Cummins, who had the perfect vantage point at the non-striker's end to witness Glenn Maxwell's unbeaten 201, is unequivocal in his assessment.</p>
                <p>Facing a target of 292 against Afghanistan in Mumbai, Australia found themselves in dire straits at 91/7 and on the brink of defeat. It was then that Maxwell delivered an innings that left even Ricky Ponting, a cricketing legend, astounded. Battling cramps, challenging bowling, and mounting pressure on the scoreboard, Maxwell expertly located gaps and sent balls soaring for sixes, propelling Australia into the semifinals.</p>
                <p>"It's just mind-boggling. I'm at a loss for words to describe this," remarked Cummins during the post-match presentation following Australia's nail-biting three-wicket triumph. "It's a fantastic victory, but Maxy's performance was truly extraordinary.<br><br>&nbsp;</p>
            """.trimIndent(),
            status = true,
            createdAt = "2023-11-08T11:43:41.395Z",
            updatedAt = "2023-11-08T11:43:42.632Z",
            publishedAt = "2023-11-08T11:43:42.626Z",
            seo = null,
            postMedia = PostMedia(
                data = Data(
                    id = 54,
                    attributes = DataAttributes(
                        name = "Screenshot_39.jpg",
                        alternativeText = null,
                        caption = null,
                        width = 589,
                        height = 396,
                        formats = Formats(
                            thumbnail = Large(
                                name = "thumbnail_Screenshot_39.jpg",
                                hash = "thumbnail_Screenshot_39_f26c6a18f2",
                                ext = ".jpg",
                                mime = "image/jpeg",
                                path = null,
                                width = 232,
                                height = 156,
                                size = 9.35,
                                url = "/uploads/thumbnail_Screenshot_39_f26c6a18f2.jpg"
                            ),
                            small = Large(
                                name = "small_Screenshot_39.jpg",
                                hash = "small_Screenshot_39_f26c6a18f2",
                                ext = ".jpg",
                                mime = "image/jpeg",
                                path = null,
                                width = 500,
                                height = 336,
                                size = 29.72,
                                url = "/uploads/small_Screenshot_39_f26c6a18f2.jpg"
                            )
                        ),
                        hash = "Screenshot_39_f26c6a18f2",
                        ext = ".jpg",
                        mime = "image/jpeg",
                        size = 38.93,
                        url = "/uploads/Screenshot_39_f26c6a18f2.jpg",
                        previewUrl = null,
                        provider = "local",
                        providerMetadata = null,
                        createdAt = "2023-11-08T11:43:37.512Z",
                        updatedAt = "2023-11-08T11:43:37.512Z"
                    )
                )
            ),
            category = Category(id = 56)
        )
    )
}
