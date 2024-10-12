package com.pgillis.paper.feature.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pgillis.paper.model.Item

@Composable
fun FeedScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: FeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FeedScreen(
        modifier,
        uiState,
        viewModel.isRefreshing,
        viewModel::refreshFeed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeedScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    uiState: FeedUiState,
    isRefreshing: Boolean,
    refreshItems: () -> Unit
) {
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = refreshItems
    ) {
        when (uiState) {
            FeedUiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is FeedUiState.Success -> FeedItemList(itemList = uiState.list)
        }
    }
}

@Composable
private fun FeedItemList(
    modifier: Modifier = Modifier.fillMaxSize(),
    itemList: List<Item>
) {
    LazyColumn(modifier) {
        items(itemList) { item ->
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
                    Text(item.listId.toString())
                    Text(item.name)
                    Text(item.id.toString())
                }
            }
        }
    }
}

@Preview
@Composable
private fun FeedScreenPreview() {
    FeedScreen(
        uiState = FeedUiState.Success(listOf(Item(1, 123, "Some Data"))),
        isRefreshing = false,
        refreshItems = {}
    )
}