package com.moingay.cricketsuper.presentation.ui.news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.moingay.cricketsuper.data.model.response.Datum
import com.moingay.cricketsuper.data.remote.SportDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    savedStateHandle: SavedStateHandle,
    private val sportDataSource: SportDataSource,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<NewsState> = MutableStateFlow(NewsState())
    val stateFlow: StateFlow<NewsState> = _stateFlow.asStateFlow()

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    25, enablePlaceholders = true
                )
            ) {
                sportDataSource
            }.flow.cachedIn(viewModelScope).collect {data->
                _stateFlow.update {
                    it.copy(
                        pagingData = flowOf(data),
                    )
                }
            }
        }
    }



}

data class NewsState(
    val news: List<Datum> = emptyList(),
    val pagingData: Flow<PagingData<Datum>> = flowOf(PagingData.empty()),
)