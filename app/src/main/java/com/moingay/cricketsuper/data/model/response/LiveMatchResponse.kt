package com.moingay.cricketsuper.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LiveMatch (
    val mid: Long? = null,
    val status: Long? = null,

    @SerializedName("status_str")
    val statusStr: String? = null,

    @SerializedName("game_state")
    val gameState: Long? = null,

    @SerializedName("game_state_str")
    val gameStateStr: String? = null,

    @SerializedName("status_note")
    val statusNote: String? = null,

    @SerializedName("day_remaining_over")
    val dayRemainingOver: String? = null,

    @SerializedName("team_batting")
    val teamBatting: String? = null,

    @SerializedName("team_bowling")
    val teamBowling: String? = null,

    @SerializedName("live_inning_number")
    val liveInningNumber: Long? = null,

    @SerializedName("live_score")
    val liveScore: LiveScore? = null,

    val commentary: Long? = null,
    val wagon: Long? = null,
    val batsmen: List<ResponseBatsman>? = null,
    val bowlers: List<Bowler>? = null,
    val commentaries: List<Commentary>? = null,
    val day: String? = null,
    val session: String? = null,

    @SerializedName("live_inning")
    val liveInning: LiveInning? = null,

    val teams: List<Team>? = null,
    val players: List<Player>? = null
)

@Serializable
data class ResponseBatsman (
    val name: String? = null,

    @SerializedName("batsman_id")
    val batsmanId: Long? = null,

    val runs: Long? = null,

    @SerializedName("balls_faced")
    val ballsFaced: Long? = null,

    val fours: Long? = null,
    val sixes: Long? = null,

    @SerializedName("strike_rate")
    val strikeRate: String? = null
)

@Serializable
data class Bowler (
    val name: String? = null,

    @SerializedName("bowler_id")
    val bowlerId: Long? = null,

    val overs: String? = null,

    @SerializedName("runs_conceded")
    val runsConceded: Long? = null,

    val wickets: Long? = null,
    val maidens: Long? = null,
    val econ: String? = null
)

@Serializable
data class Commentary (
    @SerializedName("event_id")
    val eventId: String? = null,

    val event: String? = null,

    @SerializedName("batsman_id")
    val batsmanId: String? = null,

    @SerializedName("bowler_id")
    val bowlerId: String? = null,

    val over: String? = null,
    val ball: String? = null,
    val score: String? = null,
    val commentary: String? = null,

    @SerializedName("noball_dismissal")
    val noBallDismissal: Boolean? = null,

    val text: String? = null,
    val timestamp: Long? = null,
    val run: Long? = null,

    @SerializedName("noball_run")
    val noBallRun: String? = null,

    @SerializedName("wide_run")
    val wideRun: String? = null,

    @SerializedName("bye_run")
    val byeRun: String? = null,

    @SerializedName("legbye_run")
    val legByeRun: String? = null,

    @SerializedName("bat_run")
    val batRun: String? = null,

    @SerializedName("noball")
    val noBall: Boolean? = null,
    @SerializedName("wideball")
    val wideBall: Boolean? = null,
    val six: Boolean? = null,
    val four: Boolean? = null,
    val runs: Long? = null,
    val bats: List<Bat>? = null,
    val bowls: List<Bowl>? = null
)

@Serializable
data class Bat (
    val runs: Long? = null,

    @SerializedName("balls_faced")
    val ballsFaced: Long? = null,

    val fours: Long? = null,
    val sixes: Long? = null,

    @SerializedName("batsman_id")
    val batsmanId: Long? = null
)

@Serializable
data class Bowl (
    @SerializedName("runs_conceded")
    val runsConceded: Long? = null,

    val maidens: Long? = null,
    val wickets: Long? = null,

    @SerializedName("bowler_id")
    val bowlerId: Long? = null,

    val overs: String? = null
)

@Serializable
data class LiveInning (
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

    val fielder: List<Fielder>? = null,
    @SerializedName("powerplay")
    val powerPlay: Powerplay? = null,
    val review: Review? = null,

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

    val target: String? = null,

    @SerializedName("recent_scores")
    val recentScores: String? = null,

    @SerializedName("last_five_overs")
    val lastFiveOvers: String? = null,

    @SerializedName("last_ten_overs")
    val lastTenOvers: String? = null
)

@Serializable
data class CurrentPartnership (
    val runs: Long? = null,
    val balls: Long? = null,
    val overs: String? = null,
    val batsmen: List<CurrentPartnershipBatsman>? = null
)

@Serializable
data class CurrentPartnershipBatsman (
    val name: String? = null,

    @SerializedName("batsman_id")
    val batsmanId: Long? = null,

    val runs: Long? = null,
    val balls: Long? = null
)

@Serializable
data class DidNotBat (
    @SerializedName("player_id")
    val playerId: String? = null,

    val name: String? = null
)

@Serializable
data class Equations (
    val runs: Long? = null,
    val wickets: Long? = null,
    val overs: String? = null,

    @SerializedName("bowlers_used")
    val bowlersUsed: Long? = null,

    @SerializedName("runrate")
    val runRate: String? = null
)

@Serializable
data class ExtraRuns (
    val byes: Long? = null,
    @SerializedName("legbyes")
    val legByes: Long? = null,
    val wides: Long? = null,
    @SerializedName("noballs")
    val noBalls: Long? = null,
    val penalty: String? = null,
    val total: Long? = null
)

@Serializable
data class Fielder (
    @SerializedName("fielder_id")
    val fielderId: String? = null,

    @SerializedName("fielder_name")
    val fielderName: String? = null,

    val catches: Long? = null,

    @SerializedName("runout_thrower")
    val runoutThrower: Long? = null,

    @SerializedName("runout_catcher")
    val runoutCatcher: Long? = null,

    @SerializedName("runout_direct_hit")
    val runoutDirectHit: Long? = null,

    val stumping: Long? = null,

    @SerializedName("is_substitute")
    val isSubstitute: String? = null
)

@Serializable
data class LastWicket (
    val name: String? = null,

    @SerializedName("batsman_id")
    val batsmanId: String? = null,

    val runs: String? = null,
    val balls: String? = null,

    @SerializedName("how_out")
    val howOut: String? = null,

    @SerializedName("score_at_dismissal")
    val scoreAtDismissal: Long? = null,

    @SerializedName("overs_at_dismissal")
    val oversAtDismissal: String? = null,

    @SerializedName("bowler_id")
    val bowlerId: String? = null,

    val dismissal: String? = null,
    val number: Long? = null
)

@Serializable
data class Review (
    val batting: Batting? = null,
    val bowling: Bowling? = null
)

@Serializable
data class Batting (
    @SerializedName("batting_team_total_review")
    val battingTeamTotalReview: String? = null,

    @SerializedName("batting_team_review_success")
    val battingTeamReviewSuccess: String? = null,

    @SerializedName("batting_team_review_failed")
    val battingTeamReviewFailed: String? = null,

    @SerializedName("batting_team_review_available")
    val battingTeamReviewAvailable: String? = null,

    @SerializedName("batting_team_review_retained")
    val battingTeamReviewRetained: String? = null
)

@Serializable
data class Bowling (
    @SerializedName("bowling_team_total_review")
    val bowlingTeamTotalReview: String? = null,

    @SerializedName("bowling_team_review_success")
    val bowlingTeamReviewSuccess: String? = null,

    @SerializedName("bowling_team_review_failed")
    val bowlingTeamReviewFailed: String? = null,

    @SerializedName("bowling_team_review_available")
    val bowlingTeamReviewAvailable: String? = null,

    @SerializedName("bowling_team_review_retained")
    val bowlingTeamReviewRetained: String? = null
)

@Serializable
data class LiveScore (
    val runs: Long? = null,
    val overs: String? = null,
    val wickets: Long? = null,
    val target: Long? = null,
    @SerializedName("runrate")
    val runRate: Double? = null,

    @SerializedName("required_runrate")
    val requiredRunRate: String? = null
)

@Serializable
data class Player (
    val pid: Long? = null,
    val title: String? = null,

    @SerializedName("short_name")
    val shortName: String? = null,

    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("middle_name")
    val middleName: String? = null,

    val birthdate: String? = null,
    val birthplace: String? = null,
    val country: String? = null,

    @SerializedName("primary_team")
    val primaryTeam: PrimaryTeam? = null,

    @SerializedName("logo_url")
    val logoUrl: String? = null,

    @SerializedName("playing_role")
    val playingRole: String? = null,

    @SerializedName("batting_style")
    val battingStyle: String? = null,

    @SerializedName("bowling_style")
    val bowlingStyle: String? = null,

    @SerializedName("fielding_position")
    val fieldingPosition: String? = null,

    @SerializedName("recent_match")
    val recentMatch: Long? = null,

    @SerializedName("recent_appearance")
    val recentAppearance: Long? = null,

    @SerializedName("fantasy_player_rating")
    val fantasyPlayerRating: Double? = null,

    @SerializedName("alt_name")
    val altName: String? = null,

    @SerializedName("facebook_profile")
    val facebookProfile: String? = null,

    @SerializedName("twitter_profile")
    val twitterProfile: String? = null,

    @SerializedName("instagram_profile")
    val instagramProfile: String? = null,

    @SerializedName("debut_data")
    val debutData: String? = null,

    @SerializedName("thumb_url")
    val thumbUrl: String? = null,

    val nationality: String? = null,
    val role: String? = null,

    @SerializedName("role_str")
    val roleStr: String? = null
)

@Serializable
data class PrimaryTeam(
    val id: Long? = null
)