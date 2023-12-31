package com.example.password_lab1.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert
    fun insert(user: UserEntity)

    @Update
    fun update(user: UserEntity)

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM user_table WHERE user_name = :username")
    fun getUserByName(username: String): UserEntity?

    @Query("SELECT start_milli FROM user_table WHERE user_name = :userName")
    fun getUserMilli(userName: String): Long

}