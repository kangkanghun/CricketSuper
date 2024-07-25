package com.moingay.cricketsuper.domain.interactors

import com.moingay.cricketsuper.data.repository.IRepository
import com.moingay.cricketsuper.domain.interactors.type.CricketBaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SetIsFirstTimeUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
): CricketBaseUseCase<Boolean, Unit>(dispatcher) {
    override suspend fun block(param: Boolean) = repository.setIsFirstTime(param)
}