package com.example.guau_guau.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.guau_guau.R
import com.example.guau_guau.data.repositories.ComentaryRepository
import com.example.guau_guau.databinding.FragmentComentPostBinding
import com.example.guau_guau.ui.base.BaseFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class ComentPost : BaseFragment<ComentaryViewModel, FragmentComentPostBinding, ComentaryRepository>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = runBlocking { userPreferences.userId.first() }


        // TODO: make userId non nullable
        binding.buttonSubmitComentary.setOnClickListener {
            if (userId != null) {
                val body = binding.editTextDescription.text.toString()
                viewModel.ComentarySubmit(userId, body)
            }
        }
    }


    override fun getViewModel(): Class<ComentaryViewModel> {
        TODO("Not yet implemented")
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentComentPostBinding {
        TODO("Not yet implemented")
    }

    override fun getFragmentRepository(): ComentaryRepository {
        TODO("Not yet implemented")
    }


}