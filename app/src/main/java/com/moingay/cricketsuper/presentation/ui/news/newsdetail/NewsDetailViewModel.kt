package com.moingay.cricketsuper.presentation.ui.news.newsdetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moingay.cricketsuper.data.model.response.MatchStatus
import com.moingay.cricketsuper.data.model.response.SportDetailResponse
import com.moingay.cricketsuper.data.model.response.SportStoriesResponse
import com.moingay.cricketsuper.domain.interactors.GetPostBySlugUseCase
import com.moingay.cricketsuper.utils.NetWorkResult
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getPostBySlugUseCase: GetPostBySlugUseCase,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<NewsDetailState> = MutableStateFlow(NewsDetailState())
    val stateFlow: StateFlow<NewsDetailState> = _stateFlow.asStateFlow()

    init {
        val slug: String = try {
            checkNotNull(savedStateHandle["slug"])
        } catch (_: Exception) {
            ""
        }
        Log.d("TAG", "324234: $slug")
        getPostBySlug(slug)
    }

    private fun getPostBySlug(slug: String) {
        viewModelScope.launch {
            getPostBySlugUseCase(slug).collectLatest { result ->
                result.onSuccess { networkResult ->
                    when (networkResult) {
                        is NetWorkResult.Error -> {
                            Log.d("TAG", "getPostBySlug: ${networkResult.exception}")
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
                                    sportStoriesResponse = networkResult.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

data class NewsDetailState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val sportStoriesResponse: SportDetailResponse? = null
)