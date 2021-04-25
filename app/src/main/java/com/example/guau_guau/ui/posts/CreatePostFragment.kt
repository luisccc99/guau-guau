package com.example.guau_guau.ui.posts

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.guau_guau.R
import com.example.guau_guau.data.network.GuauguauApi
import com.example.guau_guau.data.repositories.PostRepository
import com.example.guau_guau.databinding.FragmentCreatePostBinding
import com.example.guau_guau.ui.base.BaseFragment
import com.google.android.gms.common.util.Strings
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


class CreatePostFragment :
    BaseFragment<PostViewModel, FragmentCreatePostBinding, PostRepository>() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationManager: LocationManager
    private val REQUEST_LOCATION_CODE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.slide_bottom
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userId = runBlocking { userPreferences.userId.first() }

        binding.buttonSubmit.setOnClickListener {
            if (userId != null) {
                val title = binding.editTextTitle.text.toString()
                val body = binding.editTextDescription.text.toString()
                if (!Strings.isEmptyOrWhitespace(title) && !Strings.isEmptyOrWhitespace(body)) {
                    viewModel.postSubmit(userId, title, body)
                }
                Toast.makeText(requireContext(), "Invalid data", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCancelPost.setOnClickListener {
            view.findNavController().navigate(R.id.action_createPostFragment_to_postsFragment)
        }

        binding.goToPhoto.setOnClickListener {
            view.findNavController().navigate(R.id.action_createPostFragment_to_photoSelecTaker)
        }

        binding.buttonLocation.setOnClickListener {
            getCurrentLocation()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentLocation() {
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!gpsEnabled && !networkEnabled) {
            AlertDialog.Builder(requireContext())
                .setMessage("Please, enable your location.")
                .setPositiveButton(
                    "Open location settings"
                ) { _, _ ->
                    requireContext().startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // TODO: create AlertDialog
                } else {
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_LOCATION_CODE
                    )
                }
            } else {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    binding.textViewLocation.text =
                        "Longitude: ${it.longitude}\nLatitude: ${it.latitude}"
                }

            }
        }
    }

    override fun getViewModel() = PostViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentCreatePostBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        PostRepository(remoteDataSource.buildApi(GuauguauApi::class.java))

    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_CODE) {
            if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                grantResults[0] == REQUEST_LOCATION_CODE
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    binding.textViewLocation.text =
                        "Longitude: ${it.longitude}\nLatitude: ${it.latitude}"
                }
            }
        }
    }
}