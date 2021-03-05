package com.example.guau_guau.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.ui.HomeActivity
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.AuthRepository
import com.example.guau_guau.ui.base.BaseFragment
import com.example.guau_guau.databinding.FragmentLoginBinding
import com.example.guau_guau.ui.enable
import com.example.guau_guau.ui.startNewActivity
import com.example.guau_guau.ui.visible

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(false)
            when (it) {
                is Resource.Success -> {
                    viewModel.saveAuthToken(it.value.token)
                    requireActivity().startNewActivity(HomeActivity::class.java)
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
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
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            binding.progressbar.visibility = View.VISIBLE
            viewModel.login(email, password)

        }
    }


    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)


    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(GuauguauApi::class.java), userPreferences)

}