package com.moingay.cricketsuper.domain.interactors

import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.ResponseApi
import com.moingay.cricketsuper.data.repository.IRepository
import com.moingay.cricketsuper.domain.interactors.type.CricketBaseUseCaseFlow
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetIsFirstTimeUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : CricketBaseUseCaseFlow<Unit, Boolean>(dispatcher) {
    override suspend fun build(param: Unit): Flow<Boolean> = repository.getIsFirstTime()
}