package com.example.guau_guau.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.guau_guau.R
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
        val cancelButton = view.findViewById<Button>(R.id.button_cancel_edit_name)
        val acceptButton = view.findViewById<Button>(R.id.button_edit_name)
        cancelButton.setOnClickListener { dismiss() }

        acceptButton.setOnClickListener {
            val name = view.findViewById<EditText>(R.id.edit_text_name).text.toString()
            val lastName = view.findViewById<EditText>(R.id.edit_text_last_name).text.toString()
            val action = NameBottomSheetFragmentDirections
                .actionNameBottomSheetFragmentToProfileFragment(name, lastName)
            findNavController().navigate(action)
        }

    }
}