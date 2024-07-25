package com.moingay.cricketsuper.presentation.ui.series.seriesdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.remote.ICricketApi
import com.moingay.cricketsuper.data.remote.SeriesMatchDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf

class SeriesDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val iCricketApi: ICricketApi,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<SeriesDetailState> =
        MutableStateFlow(SeriesDetailState())

    val stateFlow: StateFlow<SeriesDetailState> = _stateFlow.asStateFlow()

    init {
        val competitionId: Long = try {
            checkNotNull(savedStateHandle["competitionId"])
        } catch (_: Exception) {
            0L
        }
        val titleSeries: String = try {
            checkNotNull(savedStateHandle["title"])
        } catch (_: Exception) {
            ""
        }
        _stateFlow.update {
            it.copy(
                titleSeries = titleSeries
            )
        }
        getCompetitionMatches(competitionId)
    }

    private fun getCompetitionMatches(competitionId: Long) {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    10, enablePlaceholders = true
                )
            ) {
                SeriesMatchDataSource(competitionId, iCricketApi)
            }.flow.cachedIn(viewModelScope).collect { data ->
                _stateFlow.update {
                    it.copy(
                        pagingDataItem = flowOf(data),
                    )
                }
            }
        }
    }

}

data class SeriesDetailState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val pagingDataItem: Flow<PagingData<Item>> = flowOf(PagingData.empty()),
    val titleSeries: String = "",
)