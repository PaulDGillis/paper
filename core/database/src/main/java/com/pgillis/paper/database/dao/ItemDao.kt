package com.pgillis.paper.database.dao

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.pgillis.paper.database.model.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items ORDER BY list_id, name")
    suspend fun getItems(): Flow<List<ItemEntity>>

    @Upsert
    suspend fun upsertItems(items: List<ItemEntity>)
}