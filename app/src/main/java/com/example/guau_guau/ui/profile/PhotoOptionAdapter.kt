package com.example.guau_guau.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.guau_guau.R

class PhotoOptionAdapter(
    private val context: Context,
    private val dataSource: Array<PhotoOption>
) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount() = dataSource.size

    override fun getItem(position: Int) = dataSource[position]

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.photo_option_item, parent, false)
        val imageView = rowView.findViewById<ImageView>(R.id.ic)
        val textView = rowView.findViewById<TextView>(R.id.text_view_option)
        val photoOption = getItem(position)
        imageView.setImageResource(photoOption.photoResource)
        textView.text = photoOption.textOption
        return rowView
    }


}