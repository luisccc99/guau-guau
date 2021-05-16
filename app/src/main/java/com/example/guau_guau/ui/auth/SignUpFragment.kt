package com.example.guau_guau.ui.auth

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
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
import com.example.guau_guau.ui.handleApiError
import com.example.guau_guau.ui.visible

class SignUpFragment : BaseFragment<SignUpViewModel, FragmentSignupBinding, SignUpRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.signupResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), getString(R.string.user_created_message), Toast.LENGTH_SHORT).show()
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

        binding.buttonSignup.setOnClickListener {
            if (confirmPassword() && isEmailValid()) {
                signup()
            }
        }
    }

    private fun confirmPassword(): Boolean {
        val valid =
            binding.editTextPassword1.text.toString() == binding.editTextConfirmPassword.text.toString()
        if (!valid) {
            binding.textInputLayoutConfirm.error = getString(R.string.password_error_message)
            return false
        }
        return true
    }


    // check if email has the correct pattern and it's not empty
    private fun isEmailValid(): Boolean {
        val email = binding.editTextEmail.text.toString()
        val valid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && !TextUtils.isEmpty(email)
        if (!valid) {
            binding.textViewEmail.error = getString(R.string.email_error_message)
            return false
        }
        return true
    }

    // gets data from necessary fields and performs the sign up
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