package com.moingay.cricketsuper.domain.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.moingay.cricketsuper.data.datastore.DataStoreManager
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.LiveMatch
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.ResponseApi
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.data.model.response.SportDetailResponse
import com.moingay.cricketsuper.data.model.response.SportStoriesResponse
import com.moingay.cricketsuper.data.remote.ICricketApi
import com.moingay.cricketsuper.data.remote.ISportApi
import com.moingay.cricketsuper.data.remote.SeriesMatchDataSource
import com.moingay.cricketsuper.data.repository.IRepository
import com.moingay.cricketsuper.utils.NetWorkResult
import com.moingay.cricketsuper.utils.toResultFlow
import kotlinx.coroutines.flow.Flow

class RepositoryImpl(
    private val iCricketApi: ICricketApi,
    private val iSportApi: ISportApi,
    private val context: Context,
    private val dataStoreManager: DataStoreManager
) : IRepository {
    override suspend fun getSportPost(): Flow<NetWorkResult<SportStoriesResponse>> =
        toResultFlow(context) {
            iSportApi.getSportPost(1)
        }

    override suspend fun getPostBySlug(slug: String): Flow<NetWorkResult<SportDetailResponse>> =
        toResultFlow(context) {
            iSportApi.getPostBySlug(slug)
        }

    override suspend fun getScoreMatchDetails(matchId: Long): Flow<NetWorkResult<MatchData<ScoreMatchResponse>>> =
        toResultFlow(context) {
            iCricketApi.getScoreMatchDetails(matchId)
        }

    override suspend fun getMatchData(type: Int): Flow<NetWorkResult<MatchData<ResponseApi<Item>>>> =
        toResultFlow(context) {
            iCricketApi.getMatchData(status = type)
        }

    override suspend fun getCompetitionYearMonth(): Flow<NetWorkResult<MatchData<ResponseApi<Competition>>>> =
        toResultFlow(context) {
            iCricketApi.getCompetitionYearMonth()
        }

    override suspend fun getLiveMatchDetails(matchId: Long): Flow<NetWorkResult<MatchData<LiveMatch>>> =
        toResultFlow(context) {
            iCricketApi.getLiveMatchDetails(matchId)
        }

    override suspend fun getLiveCompetition(status: String): Flow<NetWorkResult<MatchData<ResponseApi<Competition>>>> =
        toResultFlow(context) {
            iCricketApi.getLiveCompetitions(status = status)
        }

    override suspend fun getIsFirstTime(): Flow<Boolean> = dataStoreManager.isFirstTime

    override suspend fun setIsFirstTime(isFirstTime: Boolean) =
        dataStoreManager.setIsFirstTime(isFirstTime)
}