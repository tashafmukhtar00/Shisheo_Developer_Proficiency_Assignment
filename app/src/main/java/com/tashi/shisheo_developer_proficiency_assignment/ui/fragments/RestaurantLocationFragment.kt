package com.tashi.shisheo_developer_proficiency_assignment.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.net.PlacesClient
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.tashi.shisheo_developer_proficiency_assignment.R
import com.tashi.shisheo_developer_proficiency_assignment.ui.common.Common
import com.tashi.shisheo_developer_proficiency_assignment.ui.model.MyPlaces
import com.tashi.shisheo_developer_proficiency_assignment.ui.remote.IGoogleApiService
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback


class RestaurantLocationFragment : Fragment(), OnMapReadyCallback, PermissionListener {

    private lateinit var myPlacesService: IGoogleApiService
    private lateinit var restButton: Button

    companion object {
        private const val MY_PERMISSION_CODE: Int = 1

    }

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var mLastLocation: Location
    private var mMarker: Marker? = null


    // Location

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback


    private lateinit var placesClient: PlacesClient
    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView


    internal lateinit var currentPlace: MyPlaces

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_restaurant_location, container, false)
        mapView = view.findViewById(R.id.map_view)
        restButton = view.findViewById(R.id.button_resturants)
        restButton.setOnClickListener {
            nearbyRestaurants()
        }


        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(this)
            .check()

        // inti places api service
        myPlacesService = Common.googleApiService

        loadLocationData()





        return view
    }

    private fun loadLocationData() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)


            if (checkLocationPermission()) {

                buildLocationRequest()
                buildLocationCallback()


                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context!!)
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()


                )


            } else {

                buildLocationRequest()
                buildLocationCallback()


                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(context!!)
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )

            }
    }

    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                // super.onLocationResult(p0)

                mLastLocation = p0!!.locations.get(p0.locations.size - 1)  // Get last location

                if (mMarker != null) {

                    mMarker!!.remove()
                }

                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                val latLng = LatLng(latitude, longitude)
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Your location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))

                mMarker = googleMap.addMarker(markerOptions)
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }


        }


    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f


    }

    private fun checkLocationPermission(): Boolean {


        if (ContextCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity!!,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSION_CODE
            )
            else
                ActivityCompat.requestPermissions(
                    activity!!, arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), MY_PERMISSION_CODE
                )
            return false
        } else
            return true

    }

    // override onRequestPermissionResult


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            MY_PERMISSION_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(
                        activity!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    googleMap.isMyLocationEnabled = true

                    buildLocationRequest()
                    buildLocationCallback()


                    fusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(context!!)
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.myLooper()
                    )
                    //      loadLocationData()

                }
                return

            } else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!

        // init google play services

        if (ContextCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true


        } else {
            //    googleMap.isMyLocationEnabled = true
        }

        // enable zoom control

        googleMap.uiSettings.isZoomControlsEnabled = true


    }


    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }


    private fun nearbyRestaurants() {

        // clear all marker on maps


        //    googleMap.clear()


        val url = getUrl(latitude, longitude, "restaurant")

        Toast.makeText(context, "" + latitude, Toast.LENGTH_SHORT).show()
        myPlacesService.getNearbyPlaces(url).enqueue(object : Callback,
            retrofit2.Callback<MyPlaces> {
            override fun onResponse(call: Call<MyPlaces>, response: Response<MyPlaces>) {

                currentPlace = response.body()!!

//                Toast.makeText(context, "" + response.body(), Toast.LENGTH_SHORT).show()

                for (i in 0 until response.body()!!.results.size) {

                    val googlePlaces = response.body()!!.results[i]

                    val lat = googlePlaces.geometry.location.lat
                    val lng = googlePlaces.geometry.location.lng
                    //  Toast.makeText(context, "" +lat, Toast.LENGTH_SHORT).show()
                    val placeName = googlePlaces.name

                    // Toast.makeText(context, "" +placeName, Toast.LENGTH_SHORT).show()
                    val latLng = LatLng(lat, lng)
                    //  Toast.makeText(context, "" + latLng +placeName, Toast.LENGTH_SHORT).show()


                    val markerOptions = MarkerOptions()
                    markerOptions.position(latLng)
                    markerOptions.title(placeName)
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE))


                    markerOptions.snippet(i.toString())

                    // add marker to map

                    googleMap.addMarker(markerOptions)


                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                    googleMap.animateCamera(
                        CameraUpdateFactory.zoomTo(13f)
                    )

                }


            }

            override fun onFailure(call: Call<MyPlaces>, t: Throwable) {

                Toast.makeText(context, "Failed to load places", Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun getUrl(latitude: Double, longitude: Double, placeType: String): String {

        val googlePlaceUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
                .append("?location=$latitude,$longitude")
                .append("&rankby=distance")

                .append("&type=$placeType")
                .append("&key=AIzaSyDQMnCTH0Q3nWz6juS6wFYyjaHAgtaRGu0")

        Log.d("URL", "getUrl: $googlePlaceUrl")


        return googlePlaceUrl.toString()

        //.append("&keyword=cruise")

    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
        loadLocationData()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        TODO("Not yet implemented")
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }


}