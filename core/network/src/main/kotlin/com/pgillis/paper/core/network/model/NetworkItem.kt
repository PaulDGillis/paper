package com.pgillis.paper.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NetworkItem(
    val id: Int,
    val listId: Int,
    val name: String?
)