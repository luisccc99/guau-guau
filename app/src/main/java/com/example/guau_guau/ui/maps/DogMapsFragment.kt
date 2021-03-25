package com.example.guau_guau.ui.maps

import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.guau_guau.R
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.android.gms.tasks.Task
import java.security.AccessController.checkPermission
import java.util.jar.Manifest
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.example.guau_guau.databinding.FragmentDogMapsBinding

class DogMapsFragment : Fragment() {
    lateinit var currentLocation: Location,
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient,
    private val REQUEST_LOCATION = 1
    private val malaysiaCoordinate = LatLng(28.666534900000002, -106.0802643)
    private lateinit var mGoogleMap: GoogleMap

    private val callback = OnMapReadyCallback { googleMap ->


        var locations: ArrayList<LatLng>? = ArrayList()
            locations!!.add(LatLng(28.6605208, -106.0665705))
            locations!!.add(LatLng(28.6387397, -106.0871471))
            locations!!.add(LatLng(28.6728239, -106.0908109))
            locations!!.add(LatLng(28.6113238, -106.1068161))

        val size = locations.size

        var name_locations: ArrayList<String>? = ArrayList()
            name_locations!!.add("Mundo Patitas")
            name_locations!!.add("Amigos de los animales")
            name_locations!!.add("Centro Antirrábico")
            name_locations!!.add("Comapañía Canina")

        for (i in 0..size - 1)
            googleMap.addMarker(MarkerOptions().position(locations[i]).title(name_locations[i])).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.paws))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.buttonLogin.setOnClickListener {
            login()
        }
        return inflater.inflate(R.layout.fragment_dog_maps, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ){

        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            } else {
                Toast.makeText(context, "Necesitamos los permisos para encontrarte.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun fetchLocation(){
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it, android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(
                    it, android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )


    } else {
            val task: Task<Location> = fusedLocationProviderClient.lastLocation

            task.addOnSuccessListener {

                if (it != null) {
                    currentLocation = it
                    animateZoomInCamera(
                        LatLng(
                            currentLocation.latitude,
                            currentLocation.longitude
                        )
                    )
                }

                val REQUEST_CHECK_STATE = 12300 // any suitable ID
                val builder = LocationSettingsRequest.Builder()
                    .addLocationRequest(reqSetting)

                val client = LocationServices.getSettingsClient(requireActivity())
                client.checkLocationSettings(builder.build()).addOnCompleteListener { task ->
                    try {
                        val state: LocationSettingsStates = task.result!!.locationSettingsStates
                        Log.d("salam", task.result!!.toString())
                        Log.e(
                            "LOG", "LocationSettings: \n" +
                                    " GPS present: ${state.isGpsPresent} \n" +
                                    " GPS usable: ${state.isGpsUsable} \n" +
                                    " Location present: " +
                                    "${state.isLocationPresent} \n" +
                                    " Location usable: " +
                                    "${state.isLocationUsable} \n" +
                                    " Network Location present: " +
                                    "${state.isNetworkLocationPresent} \n" +
                                    " Network Location usable: " +
                                    "${state.isNetworkLocationUsable} \n"
                        )
                    } catch (e: RuntimeExecutionException) {
                        Log.d("salam", "hei")
                        if (e.cause is ResolvableApiException)
                            (e.cause as ResolvableApiException).startResolutionForResult(
                                requireActivity(),
                                REQUEST_CHECK_STATE
                            )
                    }
                }

                val locationUpdates = object : LocationCallback() {
                    override fun onLocationResult(lr: LocationResult) {
                        Log.e("salam", lr.toString())
                        Log.e("salam", "Newest Location: " + lr.locations.last())
                        // do something with the new location...
                        animateZoomInCamera(
                            LatLng(
                                lr.locations.last().latitude,
                                lr.locations.last().longitude
                            )
                        )
                    }
                }

                fusedLocationProviderClient.requestLocationUpdates(
                    reqSetting,
                    locationUpdates,
                    null /* Looper */
                )

                fusedLocationProviderClient.removeLocationUpdates(locationUpdates)
            }
        }
    }

    fun animateZoomInCamera(latLng: LatLng) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
    }

    private val reqSetting = LocationRequest.create().apply {
        fastestInterval = 10000
        interval = 10000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        smallestDisplacement = 1.0f
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mGoogleMap = googleMap!!
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(malaysiaCoordinate))
        animateZoomInCamera(malaysiaCoordinate)
        mGoogleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        mGoogleMap.uiSettings.isZoomControlsEnabled = true

    }


}
