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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AboutBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonCancel = view.findViewById<Button>(R.id.button_cancel_edit_about)
        val buttonAccept = view.findViewById<Button>(R.id.button_edit_about)
        buttonCancel.setOnClickListener {
            dismiss()
        }
        buttonAccept.setOnClickListener {
            val about = view.findViewById<EditText>(R.id.edit_text_about).text.toString()
            val action = AboutBottomSheetFragmentDirections
                .actionAboutBottomSheetFragmentToProfileFragment(about)
            findNavController().navigate(action)
        }
    }

}