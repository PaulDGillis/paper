package com.pgillis.paper.feature.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier,
    viewModel: FeedViewModel = hiltViewModel()
) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiState by viewModel.searchQueryItems.collectAsStateWithLifecycle()

    FeedScreen(
        modifier,
        uiState,
        viewModel.isRefreshing,
        viewModel::refreshFeed,
        viewModel::updateSearch
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedScreen(
    modifier: Modifier = Modifier,
    uiState: FeedUiState,
    isRefreshing: Boolean,
    refreshItems: () -> Unit,
    updateSearchQuery: (String) -> Unit
) {
    Column(modifier = modifier) {
        var searchQuery by rememberSaveable() { mutableStateOf("") }
        LaunchedEffect(searchQuery) {
            snapshotFlow { searchQuery }
                .collect { updateSearchQuery(searchQuery) }
        }
        TextField(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            value = searchQuery,
            onValueChange = { searchQuery = it }
        )
        PullToRefreshBox(
            modifier = Modifier.fillMaxWidth().weight(1f),
            isRefreshing = isRefreshing,
            onRefresh = refreshItems
        ) {
            if (!isRefreshing) {
                when (uiState) {
                    FeedUiState.Loading ->
                        CircularProgressIndicator(Modifier.fillMaxSize().padding(10.dp))
                    is FeedUiState.Success -> FeedScreenContent(uiState)
                }
            }
        }
    }
}

@Composable
fun FeedScreenContent(
    state: FeedUiState.Success
) {
    val collapsedState = remember { mutableStateMapOf<Int, Boolean>() }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        state.list.forEach { categoryItem ->
            val isCollapsed = collapsedState[categoryItem.listId] ?: false
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ListId ${categoryItem.listId}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = {
                        collapsedState[categoryItem.listId] = !isCollapsed
                    }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                }
            }
            if (!isCollapsed) {
                items(categoryItem.items) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(item.id.toString())
                            Text(item.name)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun FeedScreenPreview() {
    FeedScreen(
        uiState = FeedUiState.Loading, //Success(listOf(Item(1, 123, "Some Data"))),
        isRefreshing = false,
        refreshItems = {},
        updateSearchQuery = {}
    )
}