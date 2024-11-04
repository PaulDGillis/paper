package com.pgillis.paper.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.pgillis.paper.database.model.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT *, `rowid` FROM items ORDER BY list_id, name")
    fun observeItems(): Flow<List<ItemEntity>>

    @Query("SELECT *, `rowid` FROM items WHERE name LIKE '%' || :name || '%' ORDER BY list_id, name")
    fun searchItems(name: String): Flow<List<ItemEntity>>

    @Upsert
    suspend fun upsertItems(items: List<ItemEntity>)

    @Query("DELETE FROM items")
    suspend fun deleteItems()
}