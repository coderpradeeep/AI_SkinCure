package com.example.aiskincure.Room

import Database.DataType.ChatDataType
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {

    @Query("SELECT * FROM ChatDataType")
    fun getAllChat() : LiveData<List<ChatDataType>>

    @Insert
    fun insertChat(chat: ChatDataType)

    @Query("DELETE FROM ChatDataType where id = :id")
    fun deleteMyChatList(id : Int)
}