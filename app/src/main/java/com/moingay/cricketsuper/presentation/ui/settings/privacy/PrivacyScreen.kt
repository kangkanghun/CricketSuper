package com.moingay.cricketsuper.presentation.ui.settings.privacy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import com.moingay.cricketsuper.utils.Constants

@Composable
fun PrivacyScreen(navController: NavHostController) {

    PrivacyScreen(
        modifier = Modifier.fillMaxSize(),
        onBack = { navController.navigateUp() }
    )

}

@Composable
fun PrivacyScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = { /* no-op */ }
) {
    val text = Constants.readPrivacyText(LocalContext.current)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                text = "Privacy Policy",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Text(
            text = text, modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
            color = Color.White
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun PrivacyScreenPreview() {
    PrivacyScreen(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        BackgroundColorAppFirst,
                        BackgroundColorAppSecond
                    )
                )
            )
    )
}