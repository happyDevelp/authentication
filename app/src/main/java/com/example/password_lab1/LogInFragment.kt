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
            val name:String = binding.edTextNickname.text.toString()
            val password = binding.edTextPassword.text.toString()
            CoroutineScope(Dispatchers.Main).launch{
                val userInDb = findUserByName(name)
                if (userInDb != null) {
                    val nameDB = userInDb.userName
                    val passwordDB = userInDb.userPassword

                    if (name == nameDB && password == passwordDB)
                        navigateToInside()
                    else toastError()
                }
                else toastNoUser()

            }
        }
    }

    private suspend fun findUserByName(user: String): UserEntity? {
        return withContext(Dispatchers.IO) {
            myDB.userDao.getUserByName(user)
        }
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

    private fun navigateToInside(){
        findNavController().navigate(R.id.action_logInFragment_to_insideFragment)
    }

}