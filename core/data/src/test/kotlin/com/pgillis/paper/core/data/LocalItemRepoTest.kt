package com.pgillis.paper.core.data

import com.pgillis.paper.core.data.model.asEntity
import com.pgillis.paper.core.data.testdoubles.TestItemDao
import com.pgillis.paper.core.data.model.asPOKO
import com.pgillis.paper.core.data.model.isValid
import com.pgillis.paper.core.data.testdoubles.TestPaperNetworkDataSource
import com.pgillis.paper.core.network.model.NetworkItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LocalItemRepoTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var networkDataSource: TestPaperNetworkDataSource
    private lateinit var subject: LocalItemRepo

    @Before
    fun setup() {
        networkDataSource = TestPaperNetworkDataSource(
            ioDispatcher = UnconfinedTestDispatcher(),
            networkJson = Json { ignoreUnknownKeys = true }
        )

        subject = LocalItemRepo(TestItemDao(), networkDataSource)
    }

    @Test
    fun testGetItems() = testScope.runTest {
        // Meant to populate hiring.json from DemoNetworkDataSource to TestItemDao
        // Only meant for testing
        subject.update()

        assertEquals(
            networkDataSource.getItems().filter(NetworkItem::isValid).map { it.asEntity().asPOKO() },
            subject.observeItems().first()
        )
    }

    /*
     * TODO fix, I don't like the flow of refresh
     */
    @Test
    fun testRefresh() = testScope.runTest {
        // Fake Network update
        val networkItem = NetworkItem(1000, 1001, "Name")
        networkDataSource.fakeNewItems = listOf(networkItem)

        // Assert that change doesn't exist yet
        val subjectFlow = subject.observeItems()
        assertFalse(subjectFlow.first().contains(networkItem.asEntity().asPOKO()))

        // Refresh and assert change exists
        subject.refresh()
        assertTrue(subjectFlow.first().contains(networkItem.asEntity().asPOKO()))
    }
}