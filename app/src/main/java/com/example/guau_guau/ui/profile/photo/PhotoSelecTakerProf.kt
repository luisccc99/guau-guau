/*package com.example.guau_guau.ui.profile.photo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.guau_guau.R
import java.io.File

class PhotoSelecTakerProf : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_photo_bottom_sheet)
        ivFoto = findViewById(R.layout)
        btnTomarFoto = findViewById(R.id.btnTomarFoto)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
        btnTomarFoto?.setOnClickListener(View.OnClickListener { tomarFoto() })
        btnSeleccionarImagen?.setOnClickListener(View.OnClickListener { seleccionarImagen() })
    }

    fun tomarFoto() {
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
            val authorities = this.packageName + ".provider"
            val imageUri = FileProvider.getUriForFile(this, authorities, imagen)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen))
        }
        startActivityForResult(intent, TOMAR_FOTO)
    }
    fun seleccionarImagen() {
        val galeria = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(galeria, SELECT_IMAGEN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == SELECT_IMAGEN) {
            imagenUri = data!!.data
            ivFoto!!.setImageURI(imagenUri)
        } else if (resultCode == AppCompatActivity.RESULT_OK && requestCode == TOMAR_FOTO) {
            MediaScannerConnection.scanFile(this, arrayOf(path), null) { path, uri -> }
            val bitmap = BitmapFactory.decodeFile(path)
            ivFoto!!.setImageBitmap(bitmap)
        }
    }
}*/