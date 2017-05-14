package com.application.nikita.testapplication.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.nikita.testapplication.R
import com.application.nikita.testapplication.models.Photo
import com.application.nikita.testapplication.models.PhotoDao
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment: Fragment() {

    private var mPhotosList: MutableList<Photo> = ArrayList()
    lateinit private var mPhotoDao: PhotoDao
    lateinit private var mMapView: MapView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_map, container, false)

        mPhotoDao = PhotoDao()
        if (mPhotoDao.loadPhotos().size != 0) {
            mPhotosList = mPhotoDao.loadPhotos()
        }

        MapsInitializer.initialize(activity.applicationContext)
        mMapView = view.findViewById(R.id.mapView) as MapView
        mMapView.onCreate(savedInstanceState)
        mMapView.getMapAsync { googleMap ->
            googleMap?.isMyLocationEnabled = true
            for (i in 0..mPhotosList.size - 1) {
                val lat = mPhotosList[i].lat?.toDouble()
                val lng = mPhotosList[i].lng?.toDouble()
                googleMap?.addMarker(MarkerOptions().position(LatLng(lat!!, lng!!)).title("Photo ${mPhotosList[i].id}"))
                Log.d("MapFragment", "Lat: $lat Lng: $lng")
            }
        }
        mMapView.onResume()

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMapView.onResume()
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
}