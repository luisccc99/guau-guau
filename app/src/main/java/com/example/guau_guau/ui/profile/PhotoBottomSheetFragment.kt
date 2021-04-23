package com.example.guau_guau.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.guau_guau.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PhotoBottomSheetFragment : BottomSheetDialogFragment() {

    var ivFoto: ImageView? = null
    var btnTomarFoto: Button? = null
    var btnSeleccionarImagen: Button? = null
    var imagenUri: Uri? = null
    var TOMAR_FOTO = 100
    var SELECT_IMAGEN = 200
    var CARPETA_RAIZ = "MisFotosApp"
    var CARPETAS_IMAGENES = "imagenes"
    var RUTA_IMAGEN = CARPETA_RAIZ + CARPETAS_IMAGENES
    var path: String? = null

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
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
        listView.setOnItemClickListener { _, _, position, _ ->
            when(position) {
                0 -> {
                    var nombreImagen = ""
                    val fileImagen = File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN)
                    var isCreada = fileImagen.exists()
                    if (isCreada == false) {
                        isCreada = fileImagen.mkdirs()
                    }
                    if (isCreada == true) {
                        nombreImagen = (System.currentTimeMillis() / 1000).toString() + ".jpg"
                    }
                    path = Environment.getExternalStorageDirectory().toString() + File.separator + RUTA_IMAGEN + File.separator + nombreImagen
                    val imagen = File(path)
                    var intent: Intent? = null
                    intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        val authorities = activity?.packageName + ".provider"
                        val imageUri = FileProvider.getUriForFile(requireContext(), authorities, imagen)
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    } else {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen))
                    }
                    startActivityForResult(intent, TOMAR_FOTO)        }
                1 -> {
                    val galeria = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(galeria, SELECT_IMAGEN)
                }
            }
        }
    }
}