package com.moingay.cricketsuper.presentation.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withSave

@Composable
fun ShimmerCricketSuper(modifier: Modifier) {
    val transition = rememberInfiniteTransition(label = "")
    val animatedValue by transition.animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Column(modifier.clip(RoundedCornerShape(5.dp)).background(Color.Gray.copy(0.4f))) {
        repeat(5) {
            ShimmerItem(modifier = Modifier.fillMaxSize(), animatedValue = animatedValue)
        }
    }
}

@Composable
fun ShimmerItem(modifier: Modifier, animatedValue: Float) {
    Box(modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { canvas ->
                val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                    isAntiAlias = true
                }
                val width = size.width
                val height = size.height
                val colors = intArrayOf(
                    Color.Transparent.toArgb(),
                    Color.White.toArgb(),
                    Color.Transparent.toArgb()
                )
                val positions = floatArrayOf(0f, 0.5f, 1f)
                val shader = android.graphics.LinearGradient(
                    animatedValue * width, 0f, animatedValue * width + width, 0f,
                    colors, positions,
                    android.graphics.Shader.TileMode.CLAMP
                )
                paint.shader = shader

                canvas.nativeCanvas.withSave {
                    val rect = android.graphics.RectF(0f, 0f, width, height)
                    drawRoundRect(rect, 16f, 16f, paint)
                }
            }
        }
    }
}
