package com.pgillis.paper.core.data.model

import com.pgillis.paper.database.model.ItemEntity
import com.pgillis.paper.model.Item

fun ItemEntity.asPOKO() = Item(
    id = id,
    listId = listId,
    name = name
)