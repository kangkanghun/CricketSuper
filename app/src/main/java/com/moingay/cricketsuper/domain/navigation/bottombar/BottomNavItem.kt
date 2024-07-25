package com.moingay.cricketsuper.domain.navigation.bottombar

import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.domain.navigation.Destination

sealed class BottomNavItem(val route: String, val resId: Int, val label: String) {
    data object Home : BottomNavItem(Destination.HomeScreen.fullRoute, R.drawable.ic_home, "Home")
    data object Series : BottomNavItem(Destination.SeriesScreen.fullRoute, R.drawable.ic_series, "Series")
    data object Match : BottomNavItem(Destination.MatchScreen.fullRoute, R.drawable.ic_match, "Match")
    data object News : BottomNavItem(Destination.NewsScreen.fullRoute, R.drawable.ic_news, "News")
    data object Settings : BottomNavItem(Destination.SettingsScreen.fullRoute, R.drawable.ic_more, "Setting")
}