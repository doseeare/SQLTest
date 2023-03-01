package com.example.sqltest.mapper

import com.example.sqltest.SqlApp
import com.example.sqltest.db.NameEntity
import com.example.sqltest.model.NameModel

suspend fun NameEntity.map(): NameModel {
    val count = SqlApp.db.dao().getChildCount(id!!)
    return NameModel(
        id = id,
        name = name ?: "",
        parentId = parentId ?: 0,
        childCounts = count
    )
}