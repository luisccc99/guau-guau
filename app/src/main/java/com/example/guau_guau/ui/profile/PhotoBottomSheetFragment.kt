package com.example.guau_guau.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.example.guau_guau.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PhotoBottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options = arrayOf(
            PhotoOption(R.drawable.ic_baseline_camera, getString(R.string.take_photo)),
            PhotoOption(R.drawable.ic_photo_library, getString(R.string.select_from_gallery))
        )
        val adapter = PhotoOptionAdapter(requireContext(), options)
        val listView = view.findViewById<ListView>(R.id.list_view_photo)
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            when(position) {
                0 -> {
                    Toast.makeText(requireContext(), "Open Camera", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Toast.makeText(requireContext(), "Open Gallery", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}