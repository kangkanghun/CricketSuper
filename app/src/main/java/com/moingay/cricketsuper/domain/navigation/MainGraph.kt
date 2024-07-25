package com.moingay.cricketsuper.domain.navigation


sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    data object HomeScreen : NoArgumentsDestination("home")
    data object SeriesScreen : NoArgumentsDestination("series_screen")
    data object MatchScreen : NoArgumentsDestination("match_screen")
    data object NewsScreen : NoArgumentsDestination("news_screen")
    data object SettingsScreen : NoArgumentsDestination("settings_screen")
    data object PrivacyScreen : NoArgumentsDestination("privacy_screen")

    data object LiveMatchDetailScreen : Destination("live_match_detail_screen", "matchId"){
        private const val FIST_NAME_KEY = "matchId"

        operator fun invoke(id: Long): String = route.appendParams(
            FIST_NAME_KEY to id,
        )
    }
    data object SeriesDetailScreen : Destination("series_detail_screen", "competitionId", "title"){
        private const val FIST_NAME_KEY = "competitionId"
        private const val SECOND_NAME_KEY = "title"

        operator fun invoke(id: Long, title: String): String = route.appendParams(
            FIST_NAME_KEY to id,
            SECOND_NAME_KEY to title,
        )
    }

    data object NewsDetailScreen : Destination("news_detail_screen", "slug"){
        private const val FIST_NAME_KEY = "slug"

        operator fun invoke(slug: String): String = route.appendParams(
            FIST_NAME_KEY to slug,
        )
    }
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}
