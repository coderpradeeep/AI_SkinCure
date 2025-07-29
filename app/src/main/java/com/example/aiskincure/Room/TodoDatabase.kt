package com.example.aiskincure.Room

import Database.DataType.ChatDataType
import Database.DataType.UserDataType
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [ChatDataType::class, UserDataType::class ], version = 2)
abstract class TodoDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "Todo_DB"
    }

    abstract fun getTodoDao() : TodoDao
}