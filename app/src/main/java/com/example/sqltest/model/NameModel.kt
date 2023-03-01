package com.example.sqltest.model

import java.io.Serializable

class NameModel(
    val id: Int,
    val name: String,
    val parentId: Int,
    val childCounts: Int
) : Serializable