package com.example.sqltest.db

import androidx.room.Database
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [NameEntity::class], version = 1, exportSchema = true)
abstract class DataBase : RoomDatabase() {
    abstract fun dao(): Dao
}

@androidx.room.Dao
interface Dao {

    @Query("SELECT * FROM tt WHERE _parent_id = :zero LIMIT :limit OFFSET :offset")
    suspend fun getPagedParentList(limit: Int, offset: Int, zero: Int = 0): List<NameEntity>

    @Query("SELECT * FROM tt WHERE _parent_id = :id LIMIT :limit OFFSET :offset")
    suspend fun getPagesChildList(limit: Int, offset: Int, id: Int): List<NameEntity>

    @Query("SELECT COUNT(*) FROM tt WHERE _parent_id = :id")
    suspend fun getChildCount(id: Int): Int


}
