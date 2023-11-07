package com.example.password_lab1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.password_lab1.DB.UserDatabase
import com.example.password_lab1.DB.UserEntity
import com.example.password_lab1.databinding.ChangePasswordFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChangePasswordFragment : Fragment() {
    lateinit var binding: ChangePasswordFragmentBinding
    lateinit var myDB: UserDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = ChangePasswordFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDB = UserDatabase.getInstance(requireContext())

        binding.btnChangePass.setOnClickListener {
            changePass()
            moveToLogin()
        }

    }

    private fun changePass() {
        val name = binding.edTextName.text.toString()
        val newPassword = binding.edTextNewPassword.text.toString()

        if (newPassword.length >= 8 && checkString(newPassword)) {
            CoroutineScope(Dispatchers.Main).launch {
                var user = getUserByName(name)

                if (user != null) {
                    user.userPassword = newPassword
                    val currentTime = System.currentTimeMillis()
                    val updatedUser = UserEntity(user.userId, name, newPassword, currentTime)
                    userUpdate(updatedUser)

                } else toastNameNotExist()

            }
        } else toastWrongName()


    }


    private suspend fun userUpdate(user: UserEntity) {
        return withContext(Dispatchers.IO) {
            myDB.userDao.update(user)
        }
    }

    private suspend fun getUserByName(name: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            myDB.userDao.getUserByName(name)
        }


    }

    private fun toastNameNotExist() {
        Toast.makeText(
            context,
            "User with this name not exist",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun toastWrongName() {
        Toast.makeText(
            context,
            "length of name >= 4,password >= 8, pass should contain big letters",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun checkString(str: String): Boolean {
        var ch: Char
        var capitalFlag = false
        var lowerFlag = false
        var numFlag = false

        for (elem in str) {
            ch = elem
            if (ch.isUpperCase()) capitalFlag = true
            else if (ch.isLowerCase()) lowerFlag = true
            else if (ch.isDigit()) numFlag = true

            if (capitalFlag && lowerFlag && numFlag) return true
        }
        return false

    }

    fun moveToLogin() {
        findNavController().navigate(R.id.action_changePasswordFragment_to_logInFragment)
    }

}