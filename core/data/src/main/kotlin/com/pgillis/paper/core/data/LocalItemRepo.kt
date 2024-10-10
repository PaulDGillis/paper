package com.pgillis.paper.core.data

import androidx.annotation.VisibleForTesting
import com.pgillis.paper.core.data.model.asEntity
import com.pgillis.paper.core.data.model.asPOKO
import com.pgillis.paper.core.data.model.isValid
import com.pgillis.paper.core.network.PaperNetworkDataSource
import com.pgillis.paper.core.network.model.NetworkItem
import com.pgillis.paper.database.dao.ItemDao
import com.pgillis.paper.database.model.ItemEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalItemRepo @Inject constructor(
    private val itemDao: ItemDao,
    private val networkDataSource: PaperNetworkDataSource
) {

    fun getItems() = itemDao.getItems()
        .map { items -> items.map(ItemEntity::asPOKO) }

    @VisibleForTesting
    suspend fun update() {
        val netItems = networkDataSource.getItems()
            .filter(NetworkItem::isValid)
            .map(NetworkItem::asEntity)
        itemDao.upsertItems(netItems)
    }

    suspend fun refresh() {
        itemDao.deleteItems()
        update()
    }
}