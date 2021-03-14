package com.example.guau_guau.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
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
            Toast.makeText(requireContext(), "Editing name", Toast.LENGTH_SHORT).show()
        }

    }
}