package com.moingay.cricketsuper.domain.interactors

import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.data.model.response.ResponseApi
import com.moingay.cricketsuper.data.repository.IRepository
import com.moingay.cricketsuper.domain.interactors.type.CricketBaseUseCaseFlow
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetMatchDataUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : CricketBaseUseCaseFlow<MatchStatus, NetWorkResult<MatchData<ResponseApi<Item>>>>(dispatcher) {
    override suspend fun build(param: MatchStatus): Flow<NetWorkResult<MatchData<ResponseApi<Item>>>> {
        val status = when(param){
            MatchStatus.SCHEDULED -> 1
            MatchStatus.COMPLETED -> 2
            MatchStatus.LIVE -> 3
            MatchStatus.ABANDONED -> 4
        }
        return repository.getMatchData(status)
    }
}