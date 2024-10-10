package com.pgillis.paper.core.network.demo

import JvmUnitTestDemoAssetManager
import com.pgillis.paper.core.network.model.NetworkItem
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DemoPaperNetworkDataSourceTest {

    private lateinit var subject: DemoPaperNetworkDataSource

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        subject = DemoPaperNetworkDataSource(
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true },
            assets = JvmUnitTestDemoAssetManager,
        )
    }

    @Suppress("ktlint:standard:max-line-length")
    @Test
    fun testDeserializationOfItems() = runTest(testDispatcher) {
        assertEquals(
            NetworkItem(
                755,
                2,
                ""
            ),
            subject.getItems().first()
        )
    }
}