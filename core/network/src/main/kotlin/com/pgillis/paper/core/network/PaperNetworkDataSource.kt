package com.pgillis.paper.core.network

import com.pgillis.paper.core.network.model.NetworkItem

interface PaperNetworkDataSource {
    suspend fun getItems(): List<NetworkItem>
}