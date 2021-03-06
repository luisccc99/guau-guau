package com.example.guau_guau.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import androidx.navigation.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.data.UserPreferences
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.UserRepository
import com.example.guau_guau.databinding.FragmentProfileBinding
import com.example.guau_guau.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, UserRepository>() {

    private val TAG = ProfileFragment::class.java.simpleName

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = runBlocking { userPreferences.userId.first() }
        // TODO: make userId non nullable
        if (userId != null) {
            viewModel.getUser(userId)
        }
        viewModel.user.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    val user = it.value
                    with(binding) {
                        textViewName.text = "${user.name} ${user.lastname}"
                        textViewEmail.text = user.email
                        textViewNumPosts.text = user.num_posts.toString()
                        textViewSolvedPosts.text = user.resolved_posts.toString()
                        textViewAbout.text = user.aboutme

                        //TODO: save fragment state and change image URL
                        Glide.with(requireContext())
                            .load("https://www.arabianbusiness.com/public/images/2019/03/16/pewdiepie-new.jpg")
                            .dontAnimate()
                            .error(R.drawable.ic_baseline_person)
                            .placeholder(R.drawable.ic_baseline_person)
                            .into(imageViewProfilePic)

                    }
                }
                //TODO: change code bellow to show progress bar or error
                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.floatingChangePic.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_profileFragment_to_photoBottomSheetFragment)
        }

        binding.editName.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_profileFragment_to_nameBottomSheetFragment)
        }

        binding.editAbout.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_profileFragment_to_aboutBottomSheetFragment)
        }

        binding.buttonLogOut.setOnClickListener {
            logout()
        }
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(GuauguauApi::class.java, token)
        return UserRepository(api)
    }

}