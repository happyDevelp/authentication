package com.example.password_lab1.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.password_lab1.DB.UserDao

class SignUpViewModelFactory(private val dataSource: UserDao): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}