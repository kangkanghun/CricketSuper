package com.moingay.cricketsuper.domain.interactors

import com.moingay.cricketsuper.data.model.response.LiveMatch
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.repository.IRepository
import com.moingay.cricketsuper.domain.interactors.type.CricketBaseUseCaseFlow
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetLiveMatchDetailsUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : CricketBaseUseCaseFlow<Long, NetWorkResult<MatchData<LiveMatch>>>(dispatcher) {
    override suspend fun build(param: Long): Flow<NetWorkResult<MatchData<LiveMatch>>> = repository.getLiveMatchDetails(param)
}