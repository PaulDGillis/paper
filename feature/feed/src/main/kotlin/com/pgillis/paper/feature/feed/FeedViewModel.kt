package com.pgillis.paper.feature.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pgillis.paper.core.data.LocalItemRepo
import com.pgillis.paper.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FeedUiState {
    data object Loading: FeedUiState
    data class Success(val list: List<Item>): FeedUiState
}


@HiltViewModel
class FeedViewModel @Inject constructor(
    private val itemRepo: LocalItemRepo
): ViewModel() {

    var isRefreshing by mutableStateOf(false)
        private set

    // Refresh Feed for a initial pull when opening app
    init {
        refreshFeed()
    }

    val uiState = itemRepo.observeItems()
        .onCompletion { isRefreshing = false }
        .map(FeedUiState::Success)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FeedUiState.Loading
        )

    fun refreshFeed() {
        isRefreshing = true
        viewModelScope.launch {
            itemRepo.refresh()
        }
    }
}