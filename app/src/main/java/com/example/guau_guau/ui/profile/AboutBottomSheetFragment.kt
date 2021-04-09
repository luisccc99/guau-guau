package com.example.guau_guau.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.databinding.FragmentAboutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * This fragment displays a bottom sheet dialog fragment to
 * edit user's about
 */
class AboutBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_bottom_sheet, container, false)
    }

    /**
     * capture about's data and send it to profile fragment
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAboutBottomSheetBinding.bind(view)
        binding.buttonCancelEditAbout.setOnClickListener {
            dismiss()
        }
        binding.buttonEditAbout.setOnClickListener {
            val about = binding.editTextAbout.text.toString()
            val action = AboutBottomSheetFragmentDirections
                .actionAboutBottomSheetFragmentToProfileFragment(about = about)
            findNavController().navigate(action)
        }
    }

}