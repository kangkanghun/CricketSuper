package com.moingay.cricketsuper.presentation.ui.series

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.ResponseApi
import com.moingay.cricketsuper.domain.interactors.GetLiveCompetitionUseCase
import com.moingay.cricketsuper.utils.CompetitionStatus
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SeriesViewModel(
    savedStateHandle: SavedStateHandle,
    private val getLiveCompetitionUseCase: GetLiveCompetitionUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<SeriesState> = MutableStateFlow(SeriesState())

    val stateFlow: StateFlow<SeriesState> = _stateFlow.asStateFlow()

    fun onInitPagerCompetitions(competitionStatus: CompetitionStatus){
        getCompetitionByStatus(competitionStatus)
    }

    private fun getCompetitionByStatus(competitionStatus: CompetitionStatus) {
        viewModelScope.launch {
            getLiveCompetitionUseCase(competitionStatus.value).collect { result ->
                result.onSuccess { networkResult ->
                    when (networkResult) {
                        is NetWorkResult.Error -> {
                            Log.e("getOngoingCompetitions: ongoing", networkResult.exception)
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
                                )
                            }
                            when (competitionStatus) {
                                CompetitionStatus.LIVE -> {
                                    _stateFlow.update { liveMatchDetailState ->
                                        liveMatchDetailState.copy(
                                            ongoingCompetition = networkResult.data
                                        )
                                    }
                                }

                                CompetitionStatus.FIXTURE -> {
                                    _stateFlow.update { liveMatchDetailState ->
                                        liveMatchDetailState.copy(
                                            upcomingCompetition = networkResult.data
                                        )
                                    }
                                }

                                CompetitionStatus.RESULT -> {
                                    _stateFlow.update { liveMatchDetailState ->
                                        liveMatchDetailState.copy(
                                            completedCompetition = networkResult.data
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun onRefresh(competitionStatus: CompetitionStatus) {
        _stateFlow.update {
            it.copy(
                isRefreshing = true
            )
        }
        getCompetitionByStatus(competitionStatus)
    }

}

data class SeriesState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val ongoingCompetition: MatchData<ResponseApi<Competition>>? = MatchData(),
    val upcomingCompetition: MatchData<ResponseApi<Competition>>? = MatchData(),
    val completedCompetition: MatchData<ResponseApi<Competition>>? = MatchData(),
)