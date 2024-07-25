package com.moingay.cricketsuper.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moingay.cricketsuper.R

@Composable
fun LoadingData(
    modifier: Modifier = Modifier
) {
    val lottieAnim by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.anim_loader))
    Box(
        modifier
            .fillMaxSize()
            .background(Color.Gray.copy(0.4f)),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = lottieAnim,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight()
        )
    }
}

@Preview(name = "LoadingScreenGif", showSystemUi = true)
@Composable
private fun PreviewLoadingData() {
    LoadingData(
        modifier = Modifier
            .fillMaxSize()
    )
}