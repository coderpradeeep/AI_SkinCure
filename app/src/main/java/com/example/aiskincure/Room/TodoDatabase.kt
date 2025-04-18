package com.example.aiskincure.Room

import Database.DataType.ChatDataType
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ChatDataType::class ], version = 1)
abstract class TodoDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "Todo_DB"
    }

    abstract fun getTodoDao() : TodoDao
}