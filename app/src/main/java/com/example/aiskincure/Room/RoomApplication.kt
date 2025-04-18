package com.example.aiskincure.Room

import android.app.Application
import androidx.room.Room

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
            ).build()
    }
}