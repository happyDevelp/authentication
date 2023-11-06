package com.example.password_lab1

import android.app.Application
import com.example.password_lab1.DB.UserDatabase

class App: Application() {
    private lateinit var database: UserDatabase

    override fun onCreate() {
        super.onCreate()

        database = UserDatabase.getInstance(applicationContext)
    }

/*    companion object {
        private const val DATABASE_NAME =
    }*/

}