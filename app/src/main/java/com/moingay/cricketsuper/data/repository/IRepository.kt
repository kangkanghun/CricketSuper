package com.moingay.cricketsuper.data.repository

import androidx.paging.PagingData
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.LiveMatch
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.ResponseApi
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.data.model.response.SportDetailResponse
import com.moingay.cricketsuper.data.model.response.SportStoriesResponse
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getSportPost(): Flow<NetWorkResult<SportStoriesResponse>>
    suspend fun getPostBySlug(slug: String): Flow<NetWorkResult<SportDetailResponse>>
    suspend fun getScoreMatchDetails(matchId: Long): Flow<NetWorkResult<MatchData<ScoreMatchResponse>>>
    suspend fun getMatchData(type: Int): Flow<NetWorkResult<MatchData<ResponseApi<Item>>>>
    suspend fun getCompetitionYearMonth(): Flow<NetWorkResult<MatchData<ResponseApi<Competition>>>>
    suspend fun getLiveMatchDetails(matchId: Long): Flow<NetWorkResult<MatchData<LiveMatch>>>
    suspend fun getLiveCompetition(status: String): Flow<NetWorkResult<MatchData<ResponseApi<Competition>>>>
    suspend fun getIsFirstTime(): Flow<Boolean>
    suspend fun setIsFirstTime(isFirstTime: Boolean)
}