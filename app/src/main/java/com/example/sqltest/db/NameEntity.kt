package com.example.sqltest.db

import androidx.room.*
import androidx.room.ForeignKey.NO_ACTION

@Entity(
    tableName = "tt",
    foreignKeys = [ForeignKey(
        entity = NameEntity::class,
        parentColumns = ["_id"],
        childColumns = ["_parent_id"],
        onUpdate = NO_ACTION,
        onDelete = NO_ACTION
    )],
    indices = [Index("_id"), Index(
        name = "par_key",
        unique = false,
        orders = [Index.Order.ASC],
        value = ["_parent_id"]
    )]
)
class NameEntity(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int? = null,

    @ColumnInfo(name = "_name")
    val name: String? = null,

    @ColumnInfo(name = "_parent_id")
    val parentId: Int? = null
)