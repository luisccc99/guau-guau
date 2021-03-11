package com.example.guau_guau.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.SignUpRepository
import com.example.guau_guau.databinding.FragmentSignupBinding
import com.example.guau_guau.ui.base.BaseFragment

class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignupBinding, SignUpRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signupResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "user created", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "loading", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.buttonCancelSignup.setOnClickListener {
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        binding.buttonSignup.setOnClickListener {
            signup()
        }
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