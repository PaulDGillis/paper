package com.pgillis.paper.core.data.testdoubles

import com.pgillis.paper.database.dao.ItemDao
import com.pgillis.paper.database.model.ItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class TestItemDao: ItemDao {
    private val itemsStateFlow = MutableStateFlow(emptyList<ItemEntity>())

    override fun observeItems(): Flow<List<ItemEntity>> = itemsStateFlow

    override suspend fun upsertItems(items: List<ItemEntity>) = itemsStateFlow.update { oldFlowState ->
        (oldFlowState + items).distinctBy(ItemEntity::id)
    }

    override suspend fun deleteItems() = itemsStateFlow.update { emptyList() }
}