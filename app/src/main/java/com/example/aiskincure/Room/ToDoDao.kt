package com.example.aiskincure.Room

import Database.DataType.ChatDataType
import Database.DataType.UserDataType
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {

    @Query("SELECT * FROM ChatDataType")
    fun getAllChat() : LiveData<List<ChatDataType>>

    @Insert
    fun insertChat(chat: ChatDataType)

    @Query("DELETE FROM ChatDataType")
    fun deleteMyChatList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setUserProfile(user: UserDataType)

    @Query("SELECT * FROM UserDataType LIMIT 1")
    fun getUserProfile() : UserDataType

    @Update
    suspend fun updateUserProfile(user: UserDataType)

    @Query("DELETE FROM UserDataType")
    suspend fun clearUserProfile()

}