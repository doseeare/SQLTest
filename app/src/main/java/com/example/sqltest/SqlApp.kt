package com.example.sqltest

import android.app.Application
import androidx.room.Room
import com.example.sqltest.db.DataBase


open class SqlApp : Application() {

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java, "test_db"
        ).createFromAsset("test_db.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var db: DataBase
    }
}