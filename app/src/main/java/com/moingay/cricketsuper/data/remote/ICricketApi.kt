package com.moingay.cricketsuper.data.remote

import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.LiveMatch
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.ResponseApi
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.utils.CompetitionStatus
import com.moingay.cricketsuper.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ICricketApi {
    @GET("matches/{matchId}/live")
    suspend fun getLiveMatchDetails(
        @Path("matchId") matchId: Long
    ): Response<MatchData<LiveMatch>>
    @GET("matches/{matchId}/scorecard")
    suspend fun getScoreMatchDetails(
        @Path("matchId") matchId: Long
    ): Response<MatchData<ScoreMatchResponse>>

    @GET("matches")
    suspend fun getMatchData(
        @Query("status") status: Int,
        @Query("per_page") perPage: Int = Constants.MatchAPI.PER_PAGE
    ): Response<MatchData<ResponseApi<Item>>>

    @GET("competitions")
    suspend fun getCompetitionYearMonth(
        @Query("yearmonth") yearMonth: String = Constants.MatchAPI.YEAR_MONTH,
        @Query("per_page") perPage: Int = Constants.MatchAPI.PER_PAGE
    ): Response<MatchData<ResponseApi<Competition>>>

    @GET("competitions")
    suspend fun getLiveCompetitions(
        @Query("status") status: String = CompetitionStatus.LIVE.value,
        @Query("per_page") perPage: String? = null
    ): Response<MatchData<ResponseApi<Competition>>>

    @GET("competitions/{competitionId}/matches")
    suspend fun getCompetitionMatches(
        @Path("competitionId") competitionId: Long,
        @Query("paged") paged: Int,
    ): Response<MatchData<ResponseApi<Item>>>
}