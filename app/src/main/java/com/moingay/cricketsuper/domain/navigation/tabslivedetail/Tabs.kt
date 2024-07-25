package com.moingay.cricketsuper.domain.navigation.tabslivedetail

import com.moingay.cricketsuper.R
import com.moingay.cricketsuper.domain.navigation.Destination

sealed class Tabs(val resId: Int, val label: String) {
    data object Live : Tabs(R.drawable.ic_live_line, "Live")
    data object Score : Tabs(R.drawable.ic_scoreboard_outline, "Score")
    data object Commentary : Tabs(R.drawable.ic_microphone, "Commentary")
    data object Info : Tabs(R.drawable.ic_content_chart, "Info")
}


sealed class SeriesTabs(val resId: Int, val label: String) {
    data object Ongoing : SeriesTabs(R.drawable.ic_live_line, "Ongoing")
    data object Upcoming : SeriesTabs(R.drawable.ic_timer, "Upcoming")
    data object Completed : SeriesTabs(R.drawable.ic_double_check, "Completed")
}



sealed class MatchesTabs(val resId: Int, val label: String) {
    data object Live : MatchesTabs(R.drawable.ic_live_line, "Live")
    data object Upcoming : MatchesTabs(R.drawable.ic_timer, "Upcoming")
    data object Completed : MatchesTabs(R.drawable.ic_double_check, "Completed")
    data object Abandoned : MatchesTabs(R.drawable.ic_abandoned, "Abandoned")
}

sealed class HomeTopTabs(val label: String){
    data object Home: HomeTopTabs("Home")
    data object Live: HomeTopTabs("Live")
}


sealed class SeriesDetailTabs(val label: String){
    data object Fixture: SeriesDetailTabs("Fixture")
    data object PointsTable: SeriesDetailTabs("Points Table")
    data object Squads: SeriesDetailTabs("Squads")
    data object Stats: SeriesDetailTabs("Stats")
    data object News: SeriesDetailTabs("News")
}

