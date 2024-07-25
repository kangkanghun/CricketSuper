package com.moingay.cricketsuper.domain.interactors

import com.moingay.cricketsuper.data.model.response.SportDetailResponse
import com.moingay.cricketsuper.data.model.response.SportStoriesResponse
import com.moingay.cricketsuper.data.repository.IRepository
import com.moingay.cricketsuper.domain.interactors.type.CricketBaseUseCaseFlow
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetPostBySlugUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : CricketBaseUseCaseFlow<String, NetWorkResult<SportDetailResponse>>(dispatcher) {
    override suspend fun build(param: String): Flow<NetWorkResult<SportDetailResponse>> =
        repository.getPostBySlug(param)
}