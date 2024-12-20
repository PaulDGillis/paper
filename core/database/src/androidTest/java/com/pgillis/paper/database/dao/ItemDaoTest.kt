package com.pgillis.paper.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pgillis.paper.database.PaperDatabase
import com.pgillis.paper.database.model.ItemEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import java.io.IOException
import kotlin.test.assertEquals

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var itemDao: ItemDao
    private lateinit var db: PaperDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, PaperDatabase::class.java).build()
        itemDao = db.itemDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeItemEntity() = runTest {
        val items = listOf(ItemEntity(123, 2, "Not empty"))
        itemDao.upsertItems(items)
        assertEquals(items, itemDao.observeItems().first())
    }

    @Test
    @Throws(Exception::class)
    fun deleteAllItemEntities() = runTest {
        itemDao.deleteItems()
        assertEquals(emptyList(), itemDao.observeItems().first())
    }
}