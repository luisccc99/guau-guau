package com.example.guau_guau.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.databinding.FragmentNameBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NameBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNameBottomSheetBinding.bind(view)
        binding.buttonCancelEditName.setOnClickListener { dismiss() }

        binding.buttonEditName.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val lastName = binding.editTextLastName.text.toString()
            val action = NameBottomSheetFragmentDirections
                .actionNameBottomSheetFragmentToProfileFragment(name = name, lastname = lastName)
            findNavController().navigate(action)
        }

    }
}