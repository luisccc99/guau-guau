package com.example.guau_guau.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.guau_guau.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DogMapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        var zoom_level = 10.0f;
        var locations: ArrayList<LatLng>? = ArrayList()
            locations!!.add(LatLng(28.6605208, -106.0665705))
            locations!!.add(LatLng(28.6387397, -106.0871471))
            locations!!.add(LatLng(28.6728239, -106.0908109))
            locations!!.add(LatLng(28.6113238, -106.1068161))

        var name_locations: ArrayList<String>? = ArrayList()
            name_locations!!.add("Mundo Patitas")
            name_locations!!.add("Amigos de los animales")
            name_locations!!.add("Centro Antirrábico")
            name_locations!!.add("Compañía CAnina")

        for (location in locations)
            googleMap.addMarker(MarkerOptions().position(location)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.paws))


        var sydney = LatLng(28.666534900000002, -106.0802643)
        var chichi = LatLng(48.666534900000002, -126.0802643)

        //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom_level));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney, zoom_level))
        /*googleMap.addMarker(MarkerOptions().position(chichi).title("Marker in chichi"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(chichi))*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dog_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}