package com.moingay.cricketsuper.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.*

@Serializable
data class MatchData<T> (
    val status: String? = null,
    val response: T? = null,
    @SerialName("etag")
    val eTag: String? = null,
    val modified: String? = null,
    val datetime: String? = null,

    @SerializedName("api_version")
    val apiVersion: String? = null,

    val ch: Long? = null
)

@Serializable
data class ResponseApi<T> (
    val items: List<T>? = null,

    @SerializedName("total_items")
    val totalItems: Long? = null,

    @SerializedName("total_pages")
    val totalPages: Long? = null
)

@Serializable
data class Item (
    @SerializedName("match_id")
    val matchId: Long? = null,

    val title: String? = null,

    @SerializedName("short_title")
    val shortTitle: String? = null,

    val subtitle: String? = null,

    @SerializedName("match_number")
    val matchNumber: String? = null,

    val format: Long? = null,

    @SerializedName("format_str")
    val formatStr: String? = null,

    val status: Long? = null,

    @SerializedName("status_str")
    val statusStr: MatchStatus? = null,

    @SerializedName("status_note")
    val statusNote: String? = null,

    val verified: String? = null,

    @SerializedName("pre_squad")
    val preSquad: String? = null,

    @SerializedName("odds_available")
    val oddsAvailable: String? = null,

    @SerializedName("game_state")
    val gameState: Long? = null,

    @SerializedName("game_state_str")
    val gameStateStr: String? = null,

    val domestic: String? = null,
    val competition: Competition? = null,
    @SerializedName("teama")
    val teamA: Team? = null,
    @SerializedName("teamb")
    val teamB: Team? = null,

    @SerializedName("date_start")
    val dateStart: String? = null,

    @SerializedName("date_end")
    val dateEnd: String? = null,

    @SerializedName("timestamp_start")
    val timestampStart: Long? = null,

    @SerializedName("timestamp_end")
    val timestampEnd: Long? = null,

    @SerializedName("date_start_ist")
    val dateStartIst: String? = null,

    @SerializedName("date_end_ist")
    val dateEndIst: String? = null,

    val venue: Venue? = null,
    val umpires: String? = null,
    val referee: String? = null,
    val equation: String? = null,
    val live: String? = null,
    val result: String? = null,

    @SerializedName("result_type")
    val resultType: String? = null,

    @SerializedName("win_margin")
    val winMargin: String? = null,

    @SerializedName("winning_team_id")
    val winningTeamId: Long? = null,

    val commentary: Long? = null,
    val wagon: Long? = null,

    @SerializedName("latest_inning_number")
    val latestInningNumber: Long? = null,

    @SerializedName("presquad_time")
    val preSquadTime: String? = null,

    @SerializedName("verify_time")
    val verifyTime: String? = null,

    @SerializedName("match_dls_affected")
    val matchDlsAffected: String? = null,

    val day: String? = null,
    val session: String? = null,
    val toss: Toss? = null
)

@Serializable
data class Competition (
    val cid: Long? = null,
    val title: String? = null,
    val abbr: String? = null,
    val type: String? = null,
    val category: String? = null,

    @SerializedName("match_format")
    val matchFormat: String? = null,

    val season: String? = null,
    val status: String? = null,
    @SerializedName("datestart")
    val dateStart: String? = null,
    @SerializedName("dateend")
    val dateEnd: String? = null,
    val country: String? = null,

    @SerializedName("total_matches")
    val totalMatches: String? = null,

    @SerializedName("total_rounds")
    val totalRounds: String? = null,

    @SerializedName("total_teams")
    val totalTeams: String? = null
)

@Serializable
data class Team (
    @SerializedName("team_id")
    val teamId: Long? = null,

    val name: String? = null,

    @SerializedName("short_name")
    val shortName: String? = null,

    @SerializedName("logo_url")
    val logoUrl: String? = null,

    @SerializedName("scores_full")
    val scoresFull: String? = null,

    val scores: String? = null,
    val overs: String? = null
)

@Serializable
data class Toss (
    val text: String? = null,
    val winner: Long? = null,
    val decision: Long? = null
)

@Serializable
data class Venue (
    @SerializedName("venue_id")
    val venueId: String? = null,

    val name: String? = null,
    val location: String? = null,
    val country: String? = null,
    val timezone: String? = null
)

@Serializable
enum class MatchStatus {
    @SerializedName("Completed")
    COMPLETED,

    @SerializedName("Live")
    LIVE,

    @SerializedName("Scheduled")
    SCHEDULED,

    @SerializedName("Cancelled")
    ABANDONED
}