package com.example.password_lab1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.password_lab1.DB.UserDatabase
import com.example.password_lab1.DB.UserEntity
import com.example.password_lab1.databinding.SignUpFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SignUpFragment : Fragment() {
    lateinit var binding: SignUpFragmentBinding
    lateinit var myDB: UserDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = SignUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDB = UserDatabase.getInstance(requireContext())


        binding.btnCreate.setOnClickListener {
            val name = binding.edTextNickname.text.toString()
            val password = binding.edTextPassword.text.toString()

            if (name.length >= 4 && password.length >= 8 && checkString(password)) {
                CoroutineScope(Dispatchers.Main).launch {
                    val userInDb = findUserByName(name)

                    if (userInDb == null) {
                        val currentMilliSec = System.currentTimeMillis()
                        val user = UserEntity(null, name, password, currentMilliSec)
                        insertUser(user)
                        findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToInsideFragment(name))


                    } else toastAlreadyUsed()
                }

            } else toastWrongName()
        }
    }


    private suspend fun findUserByName(name: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            myDB.userDao.getUserByName(name)
        }
    }

    private suspend fun insertUser(user: UserEntity) {
        return withContext(Dispatchers.IO) {
            myDB.userDao.insert(user)
        }
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

    private fun toastAlreadyUsed() {
        Toast.makeText(
            context,
            "This name already used",
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

}