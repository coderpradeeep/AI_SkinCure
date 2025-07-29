package com.example.aiskincure.Room

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Example: Adding a new column to a table
        database.execSQL("ALTER TABLE UserDataType ADD COLUMN newColumn TEXT")
        // Add actual SQL changes according to your schema update
    }
}

// IMPORTANT This file needs to be in the "com.example.aiskincure" package
class RoomApplication : Application() {

    companion object {
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
                applicationContext,
                TodoDatabase::class.java,
                TodoDatabase.DATABASE_NAME
                ).fallbackToDestructiveMigration(false)
            .build()
    }
}