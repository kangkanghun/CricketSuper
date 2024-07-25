package com.moingay.cricketsuper.presentation.ui.livematchdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moingay.cricketsuper.data.model.response.Bowler
import com.moingay.cricketsuper.data.model.response.InningBatsman
import com.moingay.cricketsuper.data.model.response.LiveMatch
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.ScoreMatchResponse
import com.moingay.cricketsuper.domain.interactors.GetLiveMatchDetailsUseCase
import com.moingay.cricketsuper.domain.interactors.GetScoreMatchDetailUseCase
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class LiveMatchDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getLiveMatchDetailsUseCase: GetLiveMatchDetailsUseCase,
    private val getScoreMatchDetailUseCase: GetScoreMatchDetailUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<LiveMatchDetailState> =
        MutableStateFlow(LiveMatchDetailState())

    val stateFlow: StateFlow<LiveMatchDetailState> = _stateFlow.asStateFlow()

    init {
        val matchIt: Long = try {
            checkNotNull(savedStateHandle["matchId"])
        } catch (_: Exception) {
            0
        }
        _stateFlow.update {
            it.copy(
                matchId = matchIt
            )
        }
        getLiveMatchDetail(matchIt)
        getScoreCardDetail(matchIt)
        getMatchScheduled(matchIt)
    }

    private fun getMatchScheduled(matchIt: Long) {
        viewModelScope.launch {
            while (isActive) {
                delay(3000L)
                getLiveMatchDetailsUseCase(matchIt).collectLatest { result ->
                    result.onSuccess {
                        when (it) {
                            is NetWorkResult.Success -> {
                                _stateFlow.update { liveMatchDetailState ->
                                    liveMatchDetailState.copy(
                                        matchDetailResponse = it.data
                                    )
                                }
                            }

                            else -> {/* no-op */
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getScoreCardDetail(matchIt: Long) {
        viewModelScope.launch {
            getScoreMatchDetailUseCase(matchIt).collectLatest { result ->
                result.onSuccess { networkResult ->
                    when (networkResult) {
                        is NetWorkResult.Error -> {
                            Log.e("getLiveMatchDetail-score", networkResult.exception)
                            _stateFlow.update { liveMatchDetailState ->
                                liveMatchDetailState.copy(
                                    isLoading = false,
                                    isRefreshing = false
                                )
                            }
                        }

                        is NetWorkResult.Loading -> {
                            _stateFlow.update { liveMatchDetailState ->
                                liveMatchDetailState.copy(
                                    isLoading = true,
                                )
                            }
                        }

                        is NetWorkResult.Success -> {
                            _stateFlow.update { liveMatchDetailState ->
                                val listBatsmanLive =
                                    networkResult.data?.response?.innings?.map {
                                        it.batsmen?.lastOrNull() ?: InningBatsman()
                                    } ?: emptyList()
                                val listBowlersLive =
                                    networkResult.data?.response?.innings?.map {
                                        it.bowlers?.lastOrNull() ?: Bowler()
                                    }
                                        ?: emptyList()
                                liveMatchDetailState.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    scoreMatchResponse = networkResult.data,
                                    listBatsmanLive = listBatsmanLive,
                                    listBowlersLive = listBowlersLive
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getLiveMatchDetail(matchIt: Long) {
        viewModelScope.launch {
            getLiveMatchDetailsUseCase(matchIt).collectLatest { result ->
                result.onSuccess {
                    when (it) {
                        is NetWorkResult.Error -> {
                            Log.e("getLiveMatchDetail-live", it.exception)
                            _stateFlow.update { liveMatchDetailState ->
                                liveMatchDetailState.copy(
                                    isLoading = false,
                                    isRefreshing = false
                                )
                            }
                        }

                        is NetWorkResult.Loading -> {
                            _stateFlow.update { liveMatchDetailState ->
                                liveMatchDetailState.copy(
                                    isLoading = true,
                                )
                            }
                        }

                        is NetWorkResult.Success -> {
                            _stateFlow.update { liveMatchDetailState ->
                                liveMatchDetailState.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    matchDetailResponse = it.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

data class LiveMatchDetailState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val matchDetailResponse: MatchData<LiveMatch>? = null,
    val matchId: Long = 0,
    val scoreMatchResponse: MatchData<ScoreMatchResponse>? = null,
    val listBatsmanLive: List<InningBatsman> = emptyList(),
    val listBowlersLive: List<Bowler> = emptyList(),
)