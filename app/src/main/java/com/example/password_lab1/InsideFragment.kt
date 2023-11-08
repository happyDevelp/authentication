package com.example.password_lab1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.password_lab1.DB.UserDatabase
import com.example.password_lab1.DB.UserEntity
import com.example.password_lab1.databinding.InsideFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InsideFragment() : Fragment() {
    lateinit var binding: InsideFragmentBinding
    lateinit var myDB: UserDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = InsideFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myDB = UserDatabase.getInstance(requireContext())

        val userName = arguments?.getString("userNameArg")
        binding.txtHi.text = "Hi, $userName"

        binding.btnChangePass.setOnClickListener {
            findNavController().navigate(R.id.action_insideFragment_to_changePasswordFragment)
        }

        binding.btnExit.setOnClickListener {
            findNavController().navigate(R.id.action_insideFragment_to_titleFragment)
        }


    }


}