package com.example.guau_guau.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
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
        binding.buttonSignup.setOnClickListener {
            if (confirmPassword()) {
                signup()
            }
        }
    }

    private fun confirmPassword(): Boolean {
        var valid = true;
        binding.editTextConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?){
                val password = binding.editTextPassword1.text.toString()
                val confirm = s.toString();
                if (s != null) {
                    if (confirm.isNotEmpty() && password.isNotEmpty()) {
                        if (confirm != password) {
                            binding.textInputLayoutConfirm.error = "Passwords must match"
                            valid = false;
                        }
                    }
                }
            }
        })
        return valid;
    }


// return: if editTextEmail has a email pattern and it's not empty
private fun isEmailValid(): Boolean {
    val email = binding.editTextEmail.text.toString()
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
            && !TextUtils.isEmpty(email)
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