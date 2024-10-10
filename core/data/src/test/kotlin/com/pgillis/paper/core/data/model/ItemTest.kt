package com.pgillis.paper.core.data.model

import com.pgillis.paper.core.network.model.NetworkItem
import com.pgillis.paper.database.model.ItemEntity
import com.pgillis.paper.model.Item
import org.junit.Test
import kotlin.test.assertEquals

class ItemTest {
    // Empty representation of model
    val id = 100
    val listId = 123
    val name = ""

    @Test
    fun networkModelToModelEntity() {
        val networkItem = NetworkItem(id, listId, name)
        val itemEntity = ItemEntity(id, listId, name)

        assertEquals(itemEntity, networkItem.asEntity())
    }

    @Test
    fun modelEntityToModelPOKO() {
        val itemEntity = ItemEntity(id, listId, name)
        val item = Item(id, listId, name)

        assertEquals(item, itemEntity.asPOKO())
    }
}