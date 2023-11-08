package com.example.password_lab1.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val userId: Int? = null,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "user_password")
    val userPassword: String,

    @ColumnInfo(name = "start_milli")
    val startMilli: Long

)