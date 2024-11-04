package com.pgillis.paper.feature.feed

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pgillis.paper.core.data.LocalItemRepo
import com.pgillis.paper.model.CategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class FeedUiState() {
    object Loading: FeedUiState()
    class Success(val list: List<CategoryItem>): FeedUiState()
}

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val itemRepo: LocalItemRepo
): ViewModel() {

    var isRefreshing by mutableStateOf(true)
        private set

    // Refresh Feed for a initial pull when opening app
    init {
        refreshFeed()
    }

    private val searchQuery = MutableStateFlow("")

    val searchQueryItems = searchQuery
        .debounce(200)
        .flatMapLatest { latestSearch ->
            itemRepo.observeSearchItems(latestSearch)
        }.map { FeedUiState.Success(it) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            FeedUiState.Loading
        )

    fun refreshFeed() {
        isRefreshing = true
        viewModelScope.launch {
            itemRepo.refresh(onComplete = {
                isRefreshing = false
            })
        }
    }

    fun updateSearch(newSearch: String) {
        searchQuery.value = newSearch
    }
}