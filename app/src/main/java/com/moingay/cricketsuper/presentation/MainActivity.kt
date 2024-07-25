package com.moingay.cricketsuper.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moingay.cricketsuper.domain.navigation.Destination
import com.moingay.cricketsuper.domain.navigation.NavHostCricketLiveLine
import com.moingay.cricketsuper.domain.navigation.bottombar.BottomNavigationBar
import com.moingay.cricketsuper.domain.navigation.composable
import com.moingay.cricketsuper.presentation.ui.home.HomeScreen
import com.moingay.cricketsuper.presentation.ui.livematchdetail.LiveMatchDetailScreen
import com.moingay.cricketsuper.presentation.ui.matches.MatchesScreen
import com.moingay.cricketsuper.presentation.ui.news.NewsScreen
import com.moingay.cricketsuper.presentation.ui.news.newsdetail.NewsDetailScreen
import com.moingay.cricketsuper.presentation.ui.series.SeriesScreen
import com.moingay.cricketsuper.presentation.ui.series.seriesdetail.SeriesDetailScreen
import com.moingay.cricketsuper.presentation.ui.settings.SettingsScreen
import com.moingay.cricketsuper.presentation.ui.settings.privacy.PrivacyScreen
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import com.moingay.cricketsuper.presentation.ui.theme.CricketSuperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            CricketSuperTheme {
                Scaffold(
                    modifier = Modifier
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    BackgroundColorAppFirst,
                                    BackgroundColorAppSecond
                                )
                            )
                        )
                        .systemBarsPadding(),
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController, modifier = Modifier.background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        BackgroundColorAppFirst,
                                        BackgroundColorAppSecond
                                    )
                                )
                            )
                        )
                    }
                ) { scaffoldPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(scaffoldPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavigationManager(navController = navController, onFinish = this::finish)
                    }
                }
            }
        }
    }
}

@Composable
private fun NavigationManager(navController: NavHostController, onFinish: () -> Unit) {
    NavHostCricketLiveLine(
        navController = navController,
        startDestination = Destination.HomeScreen,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        BackgroundColorAppFirst,
                        BackgroundColorAppSecond
                    )
                )
            ),
    ) {
        composable(Destination.HomeScreen) { HomeScreen(navController) }
        composable(Destination.SeriesScreen) { SeriesScreen(navController) }
        composable(Destination.MatchScreen) { MatchesScreen(navController) }
        composable(Destination.NewsScreen) { NewsScreen(navController) }
        composable(Destination.SettingsScreen) { SettingsScreen(navController) }
        composable(Destination.PrivacyScreen) { PrivacyScreen(navController) }
        composable(
            destination = Destination.LiveMatchDetailScreen,
            arguments = listOf(
                navArgument("matchId") { type = NavType.LongType },
            )
        ) { LiveMatchDetailScreen(navController) }
        composable(
            destination = Destination.SeriesDetailScreen,
            arguments = listOf(
                navArgument("competitionId") { type = NavType.LongType },
                navArgument("title") { type = NavType.StringType },
            )
        ) { SeriesDetailScreen(navController) }
        composable(
            destination = Destination.NewsDetailScreen,
            arguments = listOf(
                navArgument("slug") { type = NavType.StringType },
            )
        ) { NewsDetailScreen(navController) }
    }
}

@Preview
@Composable
fun PreviewApp() {
    CricketSuperTheme {
        Scaffold(
            modifier = Modifier.systemBarsPadding(),
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                BottomNavigationBar(navController = rememberNavController())
            }
        ) { scaffoldPadding ->
            Surface(
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
                    .padding(scaffoldPadding),
                color = MaterialTheme.colorScheme.background
            ) {

            }
        }
    }
}
