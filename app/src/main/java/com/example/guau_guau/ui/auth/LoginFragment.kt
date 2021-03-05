package com.example.guau_guau.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
<<<<<<< HEAD
import com.example.guau_guau.data.network.GuauguauApi
=======
import com.example.guau_guau.HomeActivity
import com.example.guau_guau.data.network.AuthAPI
>>>>>>> d715953 (loginF_New)
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.AuthRepository
import com.example.guau_guau.ui.base.BaseFragment
import com.example.guau_guau.databinding.FragmentLoginBinding
import com.example.guau_guau.ui.enable
import com.example.guau_guau.ui.startNewActivity
import com.example.guau_guau.ui.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.buttonLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(false)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Log.wtf("TAG", "onActivityCreated: ${it.value.token}")
                        userPreferences.saveAuthToken(it.value.token)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }

        }
        )

        binding.editTextPassword.addTextChangedListener {
            val email = binding.editTextEmail.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            binding.progressbar.visible(true)
            viewModel.login(email, password)

        }
    }


    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

<<<<<<< HEAD
    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(GuauguauApi::class.java))
=======
    override fun getFragmentRepository() = AuthRepository(remoteDataSource.buildApi(AuthAPI::class.java), userPreferences)
>>>>>>> d715953 (loginF_New)
}