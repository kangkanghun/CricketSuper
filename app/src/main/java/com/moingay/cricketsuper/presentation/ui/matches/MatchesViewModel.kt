package com.moingay.cricketsuper.presentation.ui.matches

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.data.model.response.ResponseApi
import com.moingay.cricketsuper.domain.interactors.GetLiveCompetitionUseCase
import com.moingay.cricketsuper.domain.interactors.GetMatchDataUseCase
import com.moingay.cricketsuper.presentation.ui.series.SeriesState
import com.moingay.cricketsuper.utils.CompetitionStatus
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchesViewModel(
    savedStateHandle: SavedStateHandle,
    private val getMatchDataUseCase: GetMatchDataUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<MatchesUiState> = MutableStateFlow(MatchesUiState())

    val stateFlow: StateFlow<MatchesUiState> = _stateFlow.asStateFlow()

    fun onInitPagerCompetitions(matchStatus: MatchStatus) {
        getMatchData(matchStatus)
    }

    private fun getMatchData(matchStatus: MatchStatus) {
        viewModelScope.launch {
            getMatchDataUseCase(matchStatus).collectLatest { result ->
                result.onSuccess { networkResult ->
                    when (networkResult) {
                        is NetWorkResult.Error -> {
                            Log.e("getMatchData: all", networkResult.exception)
                            _stateFlow.update { uiState ->
                                uiState.copy(
                                    isLoading = false,
                                    isRefreshing = false
                                )
                            }
                        }

                        is NetWorkResult.Loading -> {
                            _stateFlow.update { uiState ->
                                uiState.copy(
                                    isLoading = true,
                                )
                            }
                        }

                        is NetWorkResult.Success -> {
                            _stateFlow.update { uiState ->
                                uiState.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                )
                            }
                            when (matchStatus) {
                                MatchStatus.COMPLETED -> _stateFlow.update {
                                    it.copy(
                                        completedMatchData = networkResult.data
                                    )
                                }

                                MatchStatus.LIVE -> _stateFlow.update {
                                    it.copy(
                                        liveMatchData = networkResult.data
                                    )

                                }

                                MatchStatus.SCHEDULED -> _stateFlow.update {
                                    it.copy(
                                        upcomingMatchData = networkResult.data
                                    )
                                }

                                MatchStatus.ABANDONED -> _stateFlow.update {
                                    it.copy(
                                        abandonedMatchData = networkResult.data
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun onRefresh(matchStatus: MatchStatus) {
        _stateFlow.update { uiState ->
            uiState.copy(
                isRefreshing = true
            )
        }
        getMatchData(matchStatus)
    }

}

data class MatchesUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val liveMatchData: MatchData<ResponseApi<Item>>? = MatchData(),
    val upcomingMatchData: MatchData<ResponseApi<Item>>? = MatchData(),
    val completedMatchData: MatchData<ResponseApi<Item>>? = MatchData(),
    val abandonedMatchData: MatchData<ResponseApi<Item>>? = MatchData(),
)