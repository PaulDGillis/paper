package com.pgillis.paper.core.data.testdoubles

import com.pgillis.paper.core.network.Dispatcher
import com.pgillis.paper.core.network.PaperDispatchers.IO
import com.pgillis.paper.core.network.demo.DemoPaperNetworkDataSource
import com.pgillis.paper.core.network.model.NetworkItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json

class TestPaperNetworkDataSource(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    networkJson: Json
): DemoPaperNetworkDataSource(ioDispatcher, networkJson) {

    var fakeNewItems = emptyList<NetworkItem>()

    override suspend fun getItems(): List<NetworkItem> = (super.getItems() + fakeNewItems)
}