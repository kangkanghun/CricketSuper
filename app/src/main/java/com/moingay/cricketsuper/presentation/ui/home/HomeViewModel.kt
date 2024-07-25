package com.moingay.cricketsuper.presentation.ui.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.moingay.cricketsuper.data.model.response.Competition
import com.moingay.cricketsuper.data.model.response.Datum
import com.moingay.cricketsuper.data.model.response.Item
import com.moingay.cricketsuper.data.model.response.MatchData
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.data.model.response.ResponseApi
import com.moingay.cricketsuper.data.remote.SportDataSource
import com.moingay.cricketsuper.domain.interactors.GetIsFirstTimeUseCase
import com.moingay.cricketsuper.domain.interactors.GetMatchDataUseCase
import com.moingay.cricketsuper.domain.interactors.GetTrendingSeriesUseCase
import com.moingay.cricketsuper.domain.interactors.SetIsFirstTimeUseCase
import com.moingay.cricketsuper.domain.navigation.tabslivedetail.HomeTopTabs
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeViewModel(
    savedStateHandle: SavedStateHandle,
    private val getTrendingSeriesUseCase: GetTrendingSeriesUseCase,
    private val getMatchDataUseCase: GetMatchDataUseCase,
    private val sportDataSource: SportDataSource,
    private val getIsFirstTimeUseCase: GetIsFirstTimeUseCase,
    private val setIsFirstTimeUseCase: SetIsFirstTimeUseCase
) : ViewModel() {
    private val _stateFlow: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val stateFlow: StateFlow<HomeState> = _stateFlow.asStateFlow()

    init {
        getIsFirstTime()
        getMatchScheduled()
        getNews()
        getTrendingSeries()
        getMatchData()
        getMatchTop()
    }

    private fun getIsFirstTime() {
        viewModelScope.launch {
            getIsFirstTimeUseCase(Unit).collect { result ->
                result.onSuccess {
                    _stateFlow.update { homeState ->
                        homeState.copy(
                            isFirstTime = it
                        )
                    }
                }
            }
        }
    }

    fun setIsNotFirstTime() {
        viewModelScope.launch {
            setIsFirstTimeUseCase(false)
        }
    }

    private fun getMatchScheduled() {
        viewModelScope.launch {
            while (isActive) {
                delay(3000L)
                val getUpcomingMatch = getMatchDataUseCase(MatchStatus.SCHEDULED)
                val getLiveMatch = getMatchDataUseCase(MatchStatus.LIVE)
                val getCompletedMatch = getMatchDataUseCase(MatchStatus.COMPLETED)

                var isLoadUpcoming = false
                var isLoadLive = false
                var isLoadCompleted = false

                var upcomingList: List<Item> = emptyList()
                var liveList: List<Item> = emptyList()
                var completedList: List<Item> = emptyList()


                val combinedFlow = combine(
                    getCompletedMatch,
                    getLiveMatch,
                    getUpcomingMatch
                ) { completed, live, upcoming ->
                    Triple(completed, live, upcoming)
                }

                combinedFlow.collect { (completed, live, upcoming) ->
                    completed.onSuccess {
                        isLoadCompleted = when (it) {
                            is NetWorkResult.Error -> true
                            is NetWorkResult.Loading -> false
                            is NetWorkResult.Success -> {
                                it.data?.response?.items?.sortedBy { item -> item.timestampStart }
                                    ?.take(8)
                                    ?.let { it1 -> completedList = it1 }
                                true
                            }
                        }
                    }.onFailure {
                        isLoadCompleted = true
                    }
                    live.onSuccess {
                        isLoadLive = when (it) {
                            is NetWorkResult.Error -> true
                            is NetWorkResult.Loading -> false
                            is NetWorkResult.Success -> {
                                it.data?.response?.items?.sortedBy { item -> item.matchId }?.take(2)
                                    ?.let { it1 -> liveList = it1 }
                                true
                            }
                        }
                    }.onFailure {
                        isLoadLive = true
                    }
                    upcoming.onSuccess {
                        isLoadUpcoming = when (it) {
                            is NetWorkResult.Error -> true
                            is NetWorkResult.Loading -> false
                            is NetWorkResult.Success -> {
                                it.data?.response?.items?.sortedBy { item -> item.timestampStart }
                                    ?.take(5)
                                    ?.let { it1 -> upcomingList = it1 }
                                true
                            }
                        }
                    }.onFailure {
                        isLoadUpcoming = true
                    }

                    if (isLoadCompleted && isLoadLive && isLoadUpcoming) {
                        _stateFlow.update { homeState ->
                            homeState.copy(
                                listTop = liveList + completedList + upcomingList
                            )
                        }
                    }

                }
            }
        }
    }

    private fun getMatchTop() {
        _stateFlow.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            val getUpcomingMatch = getMatchDataUseCase(MatchStatus.SCHEDULED)
            val getLiveMatch = getMatchDataUseCase(MatchStatus.LIVE)
            val getCompletedMatch = getMatchDataUseCase(MatchStatus.COMPLETED)

            var isLoadUpcoming = false
            var isLoadLive = false
            var isLoadCompleted = false

            var upcomingList: List<Item> = emptyList()
            var liveList: List<Item> = emptyList()
            var completedList: List<Item> = emptyList()


            val combinedFlow = combine(
                getCompletedMatch,
                getLiveMatch,
                getUpcomingMatch
            ) { completed, live, upcoming ->
                Triple(completed, live, upcoming)
            }

            combinedFlow.collect { (completed, live, upcoming) ->
                completed.onSuccess {
                    isLoadCompleted = when (it) {
                        is NetWorkResult.Error -> true
                        is NetWorkResult.Loading -> false
                        is NetWorkResult.Success -> {
                            it.data?.response?.items?.sortedBy { item -> item.timestampStart }
                                ?.take(8)
                                ?.let { it1 -> completedList = it1 }
                            true
                        }
                    }
                }.onFailure {
                    isLoadCompleted = true
                }
                live.onSuccess {
                    isLoadLive = when (it) {
                        is NetWorkResult.Error -> true
                        is NetWorkResult.Loading -> false
                        is NetWorkResult.Success -> {
                            it.data?.response?.items?.sortedBy { item -> item.matchId }?.take(2)
                                ?.let { it1 -> liveList = it1 }
                            true
                        }
                    }
                }.onFailure {
                    isLoadLive = true
                }
                upcoming.onSuccess {
                    isLoadUpcoming = when (it) {
                        is NetWorkResult.Error -> true
                        is NetWorkResult.Loading -> false
                        is NetWorkResult.Success -> {
                            it.data?.response?.items?.sortedBy { item -> item.timestampStart }
                                ?.take(5)
                                ?.let { it1 -> upcomingList = it1 }
                            true
                        }
                    }
                }.onFailure {
                    isLoadUpcoming = true
                }

                if (isLoadCompleted && isLoadLive && isLoadUpcoming) {
                    _stateFlow.update { homeState ->
                        homeState.copy(
                            isLoading = false,
                            isRefreshing = false,
                            listTop = liveList + completedList + upcomingList
                        )
                    }
                }

            }
        }
    }

    private fun getMatchData() {
        viewModelScope.launch {
            getMatchDataUseCase(MatchStatus.LIVE).collectLatest { result ->
                result.onSuccess {
                    when (it) {
                        is NetWorkResult.Error -> {
                            _stateFlow.update { homeState ->
                                homeState.copy(
                                    isLoading = false,
                                    isRefreshing = false
                                )
                            }
                        }

                        is NetWorkResult.Loading -> {
                            _stateFlow.update { homeState ->
                                homeState.copy(
                                    isLoading = true,
                                )
                            }
                        }

                        is NetWorkResult.Success -> {
                            _stateFlow.update { homeState ->
                                homeState.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    matchData = it.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getTrendingSeries() {
        viewModelScope.launch {
            getTrendingSeriesUseCase(Unit).collectLatest { result ->
                result.onSuccess {
                    when (it) {
                        is NetWorkResult.Error -> {
                            _stateFlow.update { homeState ->
                                homeState.copy(
                                    isLoading = false,
                                    isRefreshing = false
                                )
                            }
                        }

                        is NetWorkResult.Loading -> {
                            _stateFlow.update { homeState ->
                                homeState.copy(
                                    isLoading = true,
                                )
                            }
                        }

                        is NetWorkResult.Success -> {
                            _stateFlow.update { homeState ->
                                homeState.copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    trendingSeries = it.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getNews() {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(
                    25, enablePlaceholders = true
                )
            ) {
                sportDataSource
            }.flow.cachedIn(viewModelScope).collect { data ->
                _stateFlow.update {
                    it.copy(
                        pagingData = flowOf(data),
                    )
                }
            }
        }
    }

    fun onRefresh() {
        _stateFlow.update {
            it.copy(isRefreshing = true)
        }
        getTrendingSeries()
        getMatchData()
        getMatchTop()
    }

}

data class HomeState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val trendingSeries: MatchData<ResponseApi<Competition>>? = MatchData(),
    val matchData: MatchData<ResponseApi<Item>>? = MatchData(),
    val listTop: List<Item> = emptyList(),
    val pagingData: Flow<PagingData<Datum>> = flowOf(PagingData.empty()),
    val isFirstTime: Boolean = false
)