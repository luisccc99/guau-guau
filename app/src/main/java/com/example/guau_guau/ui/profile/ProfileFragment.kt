package com.example.guau_guau.ui.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.guau_guau.R
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.network.Resource
import com.example.guau_guau.data.repositories.UserRepository
import com.example.guau_guau.databinding.FragmentProfileBinding
import com.example.guau_guau.ui.base.BaseFragment
import com.example.guau_guau.ui.handleApiError
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, UserRepository>() {

    private val args: ProfileFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = runBlocking { userPreferences.userId.first() }
        if (userId != null) {
            val name = args.name
            val lastname = args.lastname
            val about = args.about
            if (about.isNotEmpty()) {
                viewModel.editUserData(userId, null, null, about)
            } else if (name.isNotEmpty() || lastname.isNotEmpty()) {
                viewModel.editUserData(userId, name, lastname, null)
            }
            showUserInfo(userId, true)
        }

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

    @SuppressLint("SetTextI18n")
    private fun showUserInfo(userId: String, currentUser: Boolean) {
        viewModel.getUser(userId)
        viewModel.user.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = false
            showUiComponents(false)
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.isVisible = false
                    showUiComponents(true)
                    val user = it.value
                    with(binding) {
                        textViewName.text = "${user.name} ${user.lastname}"
                        textViewEmail.text = user.email
                        textViewNumPosts.text = user.num_posts.toString()
                        textViewSolvedPosts.text = user.resolved_posts.toString()
                        textViewAbout.text = user.aboutme

                        //TODO: save fragment state and change image URL
                        Glide.with(this@ProfileFragment)
                            .load(user.user_photo.url)
                            .dontAnimate()
                            .error(R.drawable.ic_baseline_person)
                            .placeholder(R.drawable.ic_baseline_person)
                            .into(imageViewProfilePic)

                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                    showUiComponents(false)
                }
                is Resource.Failure -> {
                    binding.progressBar.isVisible = false
                    handleApiError(it)
                }
            }
        })
    }

    private fun showUiComponents(visible: Boolean) {
        binding.about.isVisible = visible
        binding.icAbout.isVisible = visible
        binding.editAbout.isVisible = visible
        binding.editName.isVisible = visible
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