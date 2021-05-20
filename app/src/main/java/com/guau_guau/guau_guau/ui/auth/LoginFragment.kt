package com.guau_guau.guau_guau.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.guau_guau.guau_guau.R
import com.guau_guau.guau_guau.data.network.GuauguauApi
import com.guau_guau.guau_guau.data.network.Resource
import com.guau_guau.guau_guau.data.repositories.AuthRepository
import com.guau_guau.guau_guau.ui.base.BaseFragment
import com.guau_guau.guau_guau.databinding.FragmentLoginBinding
import com.guau_guau.guau_guau.ui.*

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, {
            binding.progressbar.visible(false)
            when (it) {
                is Resource.Success -> {
                    binding.buttonLogin.enable(false)
                    viewModel.saveAuthToken(it.value.token)
                    viewModel.saveUserId(it.value.user_id)
                    viewModel.saveExpTime(it.value.exp)
                    requireActivity().startNewActivity(HomeActivity::class.java)
                }
                is Resource.Failure -> handleApiError(it) {
                    binding.buttonLogin.enable(true)
                }
                else -> {
                    binding.buttonLogin.enable(false)
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
            binding.buttonLogin.enable(false)
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