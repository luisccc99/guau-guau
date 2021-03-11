package com.example.guau_guau.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.AuthRepository
import com.example.guau_guau.ui.base.BaseFragment
import com.example.guau_guau.databinding.FragmentLoginBinding
import com.example.guau_guau.ui.*

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    viewModel.saveAuthToken(it.value.token)
                    viewModel.saveUserId(it.value.user_id)
                    requireActivity().startNewActivity(HomeActivity::class.java)
                }
                is Resource.Failure -> handleApiError(it) {
                    login()
                }
                else -> {
                    binding.progressbar.visible(true)
                }
            }
        })

        binding.textViewSignup.setOnClickListener {
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.editTextPassword.addTextChangedListener {
            val email = binding.editTextEmail.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        viewModel.login(email, password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)


    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(GuauguauApi::class.java), userPreferences)

}