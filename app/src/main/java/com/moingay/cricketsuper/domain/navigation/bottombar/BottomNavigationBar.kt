package com.moingay.cricketsuper.domain.navigation.bottombar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppFirst
import com.moingay.cricketsuper.presentation.ui.theme.BackgroundColorAppSecond
import com.moingay.cricketsuper.presentation.ui.theme.CricketSuperTheme

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Match,
        BottomNavItem.News,
        BottomNavItem.Series,
        BottomNavItem.Settings,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var isShowBottomNav by remember {
        mutableStateOf(true)
    }
    val density = LocalDensity.current
    LaunchedEffect(key1 = navBackStackEntry) {
        isShowBottomNav =
            items.find { it.route == currentRoute } != null
    }

    AnimatedContent(
        targetState = isShowBottomNav, label = "",
        transitionSpec = {
            fadeIn(animationSpec = tween(500, 150)) togetherWith
                    fadeOut(animationSpec = tween(500)) using
                    SizeTransform { initialSize, targetSize ->
                        if (targetState) {
                            keyframes {
                                // Expand horizontally first.
                                IntSize(targetSize.width, initialSize.height) at 150
                                durationMillis = 300
                            }
                        } else {
                            keyframes {
                                // Shrink vertically first.
                                IntSize(initialSize.width, targetSize.height) at 150
                                durationMillis = 300
                            }
                        }
                    }
        }
    ) { targetExpanded ->
        if (targetExpanded) {
            Box(modifier = modifier) {
                NavigationBar(
                    containerColor = Color.Transparent
                ) {
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Image(
                                    painter = painterResource(id = item.resId),
                                    contentDescription = item.label,
                                    modifier = Modifier
                                        .background(Color.Transparent)
                                        .size(30.dp),
                                    colorFilter = if (item.route != currentRoute) {
                                        ColorFilter.tint(Color.Gray)
                                    } else {
                                        ColorFilter.tint(Color.White)
                                    }
                                )
                            },
                            label = {
                                Text(text = item.label)
                            },
                            alwaysShowLabel = true,
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = false
                                        }
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = false
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                selectedTextColor = Color.White,
                                unselectedTextColor = Color.Gray
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewApp() {
    CricketSuperTheme {
        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
            bottomBar = {
                BottomNavigationBar(navController = rememberNavController())
            }
        ) { scaffoldPadding ->
            Box(
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
                    .padding(scaffoldPadding)
            )
        }
    }
}