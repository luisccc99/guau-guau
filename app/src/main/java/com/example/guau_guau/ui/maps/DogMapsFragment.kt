package com.example.guau_guau.ui.maps

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.guau_guau.databinding.FragmentDogMapsBinding
import com.example.guau_guau.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions


class DogMapsFragment : Fragment(),
    OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private var permissionDenied = false
    private val REQUEST_LOCATION = 1
    private lateinit var googleMap: GoogleMap
    private var _binding: FragmentDogMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDogMapsBinding.inflate(inflater, container, false)
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != REQUEST_LOCATION) {
            return
        } else {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            } else {
                permissionDenied = true
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            showMissingPermissionError()
        }
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    private fun showMissingPermissionError() {
        Toast.makeText(requireContext(), getString(R.string.permissions_error_message), Toast.LENGTH_SHORT)
            .show()
    }

    private fun enableMyLocation() {
        if (!::googleMap.isInitialized) return
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            googleMap.isMyLocationEnabled = true
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap ?: return

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json)
            )
            if (!success) {
                Log.e("TAG", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAG", "Can't find style. Error: ", e)
        }

        googleMap.setOnMyLocationButtonClickListener(this)
        googleMap.setOnMyLocationClickListener(this)
        showDefaultLocations()
        enableMyLocation()
    }

    override fun onMyLocationButtonClick(): Boolean {
        val gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!gpsEnabled && !networkEnabled) {
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.enable_location_message))
                .setPositiveButton(
                    getString(R.string.open_location_settings)
                ) { _, _ ->
                    requireContext().startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton(getString(R.string.cancel), null)
                .show()
        }
        return false
    }

    override fun onMyLocationClick(location: Location) = Toast.makeText(
        requireContext(),
        "latitude ${location.latitude}\nlongitude: ${location.longitude}",
        Toast.LENGTH_SHORT
    ).show()

    private fun showDefaultLocations() {
        val locations: ArrayList<LatLng> = ArrayList()
        locations.add(LatLng(28.6605208, -106.0665705))
        locations.add(LatLng(28.6387397, -106.0871471))
        locations.add(LatLng(28.6728239, -106.0908109))
        locations.add(LatLng(28.6113238, -106.1068161))

        val size = locations.size

        val locationNames: ArrayList<String> = ArrayList()
        locationNames.add(getString(R.string.mundo_patitas))
        locationNames.add(getString(R.string.amigos_de_los_animales))
        locationNames.add(getString(R.string.centro_antirrabico))
        locationNames.add(getString(R.string.compania_canina))

        for (i in 0 until size)
            googleMap.addMarker(MarkerOptions().position(locations[i]).title(locationNames[i]))
                .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.paws))
    }
}
