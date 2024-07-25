package com.moingay.cricketsuper.data.model.response
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ScoreMatchResponse (
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
    val resultType: Long? = null,

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

    @SerializedName("live_inning_number")
    val liveInningNumber: String? = null,

    val day: String? = null,
    val session: String? = null,
    val toss: Toss? = null,

    @SerializedName("current_over")
    val currentOver: String? = null,

    @SerializedName("previous_over")
    val previousOver: String? = null,

    @SerializedName("man_of_the_match")
    val manOfTheMatch: ManOfTheMatch? = null,

    @SerializedName("man_of_the_series")
    val manOfTheSeries: String? = null,

    @SerializedName("is_followon")
    val isFollowon: Long? = null,

    @SerializedName("team_batting_first")
    val teamBattingFirst: String? = null,

    @SerializedName("team_batting_second")
    val teamBattingSecond: String? = null,

    @SerializedName("last_five_overs")
    val lastFiveOvers: String? = null,

    val innings: List<Inning>? = null,
    val players: List<Player>? = null,

    @SerializedName("pre_match_odds")
    val preMatchOdds: String?= null,

    @SerializedName("day_remaining_over")
    val dayRemainingOver: String? = null,

    @SerializedName("match_notes")
    val matchNotes: List<List<String>>? = null
)

@Serializable
data class Inning (
    val iid: Long? = null,
    val number: Long? = null,
    val name: String? = null,

    @SerializedName("short_name")
    val shortName: String? = null,

    val status: Long? = null,
    @SerializedName("issuperover")
    val isSuperOver: String? = null,
    val result: Long? = null,

    @SerializedName("batting_team_id")
    val battingTeamId: Long? = null,

    @SerializedName("fielding_team_id")
    val fieldingTeamId: Long? = null,

    val scores: String? = null,

    @SerializedName("scores_full")
    val scoresFull: String? = null,

    val batsmen: List<InningBatsman>? = null,
    val bowlers: List<Bowler>? = null,
    val fielder: List<Fielder>? = null,
    @SerializedName("powerplay")
    val powerPlay: Powerplay? = null,
    val review: Review? = null,
    @SerializedName("fows")
    val fowS: List<LastWicket>? = null,

    @SerializedName("last_wicket")
    val lastWicket: LastWicket? = null,

    @SerializedName("extra_runs")
    val extraRuns: ExtraRuns? = null,

    val equations: Equations? = null,

    @SerializedName("current_partnership")
    val currentPartnership: CurrentPartnership? = null,

    @SerializedName("did_not_bat")
    val didNotBat: List<DidNotBat>? = null,

    @SerializedName("max_over")
    val maxOver: String? = null,

    val target: String? = null
)

@Serializable
data class InningBatsman (
    val name: String? = null,

    @SerializedName("batsman_id")
    val batsmanId: String? = null,

    val batting: String? = null,
    val position: String? = null,
    val role: String? = null,

    @SerializedName("role_str")
    val roleStr: String? = null,

    val runs: String? = null,

    @SerializedName("balls_faced")
    val ballsFaced: String? = null,

    val fours: String? = null,
    val sixes: String? = null,
    val run0: String? = null,
    val run1: String? = null,
    val run2: String? = null,
    val run3: String? = null,
    val run5: String? = null,

    @SerializedName("how_out")
    val howOut: String? = null,

    val dismissal: String? = null,

    @SerializedName("strike_rate")
    val strikeRate: String? = null,

    @SerializedName("bowler_id")
    val bowlerId: String? = null,

    @SerializedName("first_fielder_id")
    val firstFielderId: String? = null,

    @SerializedName("second_fielder_id")
    val secondFielderId: String? = null,

    @SerializedName("third_fielder_id")
    val thirdFielderId: String? = null
)

@Serializable
data class Powerplay (
    val p1: P1? = null
)

@Serializable
data class P1 (
    val startover: String? = null,
    val endover: String? = null
)

@Serializable
data class ManOfTheMatch (
    val pid: Long? = null,
    val name: String? = null,

    @SerializedName("thumb_url")
    val thumbUrl: String? = null
)

@Serializable
enum class NationalityEnum(val value: String) {
    @SerializedName("India") India("India");
}