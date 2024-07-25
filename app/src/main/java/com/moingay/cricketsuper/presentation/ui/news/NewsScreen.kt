package com.moingay.cricketsuper.presentation.ui.news

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.moingay.cricketsuper.data.model.response.Datum
import com.moingay.cricketsuper.domain.navigation.Destination
import com.moingay.cricketsuper.presentation.ui.components.LoadingData
import com.moingay.cricketsuper.presentation.ui.home.components.TopSeriesItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewsScreen(navController: NavHostController) {
    // TODO UI Rendering
    val newsViewModel: NewsViewModel = koinViewModel()
    val uiState by newsViewModel.stateFlow.collectAsState()

    NewsScreen(
        modifier = Modifier.fillMaxSize(),
        news = uiState.pagingData.collectAsLazyPagingItems(),
        onClickItem = {
            it.attributes?.slug?.let { slug ->
                navController.navigate(Destination.NewsDetailScreen(slug))
            }
        },
    )
}

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    news: LazyPagingItems<Datum>,
    onClickItem: (Datum) -> Unit = {/* no-op */ },
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = news.loadState) {
        if (news.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (news.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Trending Articles",
            fontSize = 25.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(15.dp)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White.copy(0.8f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(news.itemCount) { index: Int ->
                val sportNews = try {
                    news[index]
                } catch (e: Throwable) {
                    null
                }
                sportNews?.let {
                    TopSeriesItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        storiesItem = it,
                        onClickItem = onClickItem
                    )
                }
            }
            when (news.loadState.append) {
                is LoadState.Error -> {/* no-op */
                }

                LoadState.Loading -> {
                    item {
                        LoadingItem(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is LoadState.NotLoading -> {/* no-op */
                }
            }
        }
    }

    AnimatedVisibility(
        visible = news.loadState.refresh is LoadState.Loading,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LoadingData()
    }
}

@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .padding(10.dp),
            strokeWidth = 5.dp
        )
    }
}

@Composable
@Preview(name = "News")
private fun NewsScreenPreview() {
}
