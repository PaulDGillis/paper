package com.pgillis.paper.core.data.model

import com.pgillis.paper.core.network.model.NetworkItem
import com.pgillis.paper.database.model.ItemEntity

fun NetworkItem.isValid() = name?.isNotEmpty() == true

fun NetworkItem.asEntity() = ItemEntity(
    id = id,
    listId = listId,
    name = name!! // Should be fine if validation logic filters out empty name's correctly
)