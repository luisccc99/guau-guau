package com.guau_guau.guau_guau.ui.posts

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.guau_guau.guau_guau.R
import com.guau_guau.guau_guau.data.network.GuauguauApi
import com.guau_guau.guau_guau.data.network.Resource
import com.guau_guau.guau_guau.data.repositories.UserRepository
import com.guau_guau.guau_guau.databinding.FragmentAuthorProfileBinding
import com.guau_guau.guau_guau.ui.base.BaseFragment
import com.guau_guau.guau_guau.ui.handleApiError
import com.guau_guau.guau_guau.ui.profile.ProfileViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AuthorProfileFragment : BaseFragment<ProfileViewModel, FragmentAuthorProfileBinding, UserRepository>() {

    private val args: AuthorProfileFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser(args.authorId)
        viewModel.user.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = false
            binding.icAbout.isVisible = false
            binding.about.isVisible = false
            when(it) {
                is Resource.Failure ->{
                    binding.progressBar.isVisible = false
                    handleApiError(it)
                }
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.icAbout.isVisible = false
                    binding.about.isVisible = false
                }
                is Resource.Success -> {
                    with(binding) {
                        binding.icAbout.isVisible = true
                        binding.about.isVisible = true
                        progressBar.isVisible = false
                        textViewName.text = "${it.value.name} ${it.value.lastname}"
                        textViewAbout.text = it.value.aboutme
                        Glide.with(this@AuthorProfileFragment)
                            .load(it.value.user_photo.url)
                            .dontAnimate()
                            .error(R.drawable.ic_baseline_person)
                            .placeholder(R.drawable.ic_baseline_person)
                            .into(imageViewProfilePic)
                    }
                }
            }
        })
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAuthorProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(GuauguauApi::class.java, token)
        return UserRepository(api)
    }
}