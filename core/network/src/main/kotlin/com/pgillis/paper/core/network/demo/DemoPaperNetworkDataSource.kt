package com.pgillis.paper.core.network.demo

import JvmUnitTestDemoAssetManager
import com.pgillis.paper.core.network.Dispatcher
import com.pgillis.paper.core.network.PaperDispatchers.IO
import com.pgillis.paper.core.network.PaperNetworkDataSource
import com.pgillis.paper.core.network.model.NetworkItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

/**
 * [PaperNetworkDataSource] implementation that provides static news resources to aid development
 */
class DemoPaperNetworkDataSource @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Json,
    private val assets: DemoAssetManager = JvmUnitTestDemoAssetManager,
) : PaperNetworkDataSource {
    companion object {
        private const val HIRING_ASSET = "hiring.json"
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getItems(): List<NetworkItem> =
        withContext(ioDispatcher) {
            assets.open(HIRING_ASSET).use(networkJson::decodeFromStream)
        }
}