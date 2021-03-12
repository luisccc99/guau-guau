package com.example.guau_guau.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.SignUpRepository
import com.example.guau_guau.databinding.FragmentSignupBinding
import com.example.guau_guau.ui.base.BaseFragment
import com.example.guau_guau.ui.enable
import com.example.guau_guau.ui.handleApiError
import com.example.guau_guau.ui.visible

class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignupBinding, SignUpRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signupResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "user created", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
                else -> {
                    binding.progressbarSignup.visible(true)
                }
            }
        })

        binding.buttonCancelSignup.setOnClickListener {
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.editTextPassword2.addTextChangedListener {
            if (inputIsNotEmpty()) {
                binding.buttonSignup.enable(true)
            }

        }
        binding.buttonSignup.setOnClickListener {
            signup()
        }
    }

    private fun inputIsNotEmpty(): Boolean {
        val listOfEditTexts = listOf(
            binding.editTextName,
            binding.editTextLast,
            binding.editTextEmail,
            binding.editTextPassword1
        )
        for (editText in listOfEditTexts) {
            if (editText.toString().trim().isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun signup() {
        val name = binding.editTextName.text.toString()
        val lastName = binding.editTextLast.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword1.text.toString()

        viewModel.signup(name, lastName, email, password)
    }

    override fun getViewModel() = SignUpViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignupBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        SignUpRepository(remoteDataSource.buildApi(GuauguauApi::class.java))


}