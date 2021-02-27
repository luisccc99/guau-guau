package com.example.guau_guau

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.guau_guau.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext())
            .load("https://ichef.bbci.co.uk/news/976/cpsprodpb/3BB5/production/_86458251_gettyimages-494848194.jpg")
            .dontAnimate()
            .error(R.drawable.ic_baseline_account_circle)
            .into(binding.imageViewProfilePic)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}