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
import com.example.password_lab1.databinding.LoginFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LogInFragment : Fragment() {
    lateinit var binding: LoginFragmentBinding
    lateinit var myDB: UserDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDB = UserDatabase.getInstance(requireContext())

        binding.btnLogIn.setOnClickListener {
            val name = binding.edTextNickname.text.toString()
            val password = binding.edTextPassword.text.toString()
            CoroutineScope(Dispatchers.Main).launch {
                val userInDb = findUserByName(name)

                if (userInDb != null) {
                    if (password == userInDb.userPassword) {
                        val passwordExpiryDay: Long = 7
                        val diff = passwordExpiryDay - passwordAge(name)

                        if (diff >= 0) {
                            val nameDB = userInDb.userName

                            if (name == nameDB) {
                                navigateToInside(nameDB)
                                toastWhenExpired(diff)

                            } else toastError()

                        } else {
                            toastExpired()
                            navToChangePassword()
                        }
                    } else toastInvalidPassword()


                } else toastNoUser()

            }
        }

    }


    private fun navToChangePassword() {
        findNavController().navigate(R.id.action_logInFragment_to_changePasswordFragment)
    }

    private suspend fun getUserMilli(userName: String): Long {
        return withContext(Dispatchers.IO) {
            myDB.userDao.getUserMilli(userName)
        }
    }

    private suspend fun passwordAge(userName: String): Long {
        val currentTime = System.currentTimeMillis()
        val passwordTimeCreated = getUserMilli(userName)

        val passwordAge =
            (currentTime - passwordTimeCreated) / (1000 * 60 * 60 * 24) //hours-1000 * 60 * 60, minutes-1000 * 60, sec-1000
        return passwordAge
    }

    private suspend fun findUserByName(user: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            myDB.userDao.getUserByName(user)
        }
    }

    private fun toastWhenExpired(diff: Long) {
        Toast.makeText(
            context,
            "Password expire in $diff day(s)",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun toastInvalidPassword() {
        Toast.makeText(
            context,
            "Invalid password",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun toastNoUser() {
        Toast.makeText(
            context,
            "There is no user with this name",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun toastError() {
        Toast.makeText(
            context,
            "Invalid password",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun toastExpired() {
        Toast.makeText(
            context,
            "Password already expired, update it",
            Toast.LENGTH_LONG
        ).show()
    }


    private fun navigateToInside(user: String) {
        findNavController().navigate(
            LogInFragmentDirections.actionLogInFragmentToInsideFragment(user)
        )
    }

}