package com.example.password_lab1.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase:RoomDatabase() {
    abstract val userDao: UserDao

    companion object{
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context = context.applicationContext,
                        UserDatabase::class.java,
                        "user_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }

                return instance
            }

        }

    }

}