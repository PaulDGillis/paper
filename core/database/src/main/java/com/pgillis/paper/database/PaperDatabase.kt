package com.pgillis.paper.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pgillis.paper.database.dao.ItemDao
import com.pgillis.paper.database.model.ItemEntity

@Database(entities = [ItemEntity::class], version = 2)
abstract class PaperDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemDao
}