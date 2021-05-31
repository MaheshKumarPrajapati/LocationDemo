package com.example.locationdemo.location

import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


open class BaseLocationActivity: AppCompatActivity() {
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpLocation()

    }

    private fun setUpLocation() {
        locationHelper = LocationHelper(this)
        locationHelper.startLocationClient()
        lifecycle.addObserver(locationHelper)
        locationHelper.currentLocation.observe(this, androidx.lifecycle.Observer {
            updatedLocation(it)
        })
    }

    override fun onResume() {
        super.onResume()
        checkGPSStatus()
    }

    override fun onPause() {
        if(mainHandler!=null){
            mainHandler.removeCallbacksAndMessages(null)
            Log.d("Handler","stop")
        }
        super.onPause()
    }

    override fun onDestroy() {
        lifecycle.removeObserver(locationHelper);
        super.onDestroy()
    }

    private var mainHandler: Handler = Handler(Looper.getMainLooper())
    private fun checkGPSStatus() {
        Log.d("Handler","start")

        mainHandler.post(object : Runnable {
            override fun run() {
                val manager =
                        getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val statusOfGPS =
                        manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                updatedGpsStatus(statusOfGPS)
                mainHandler.postDelayed(this, 1000)
                Log.d("Handler","called")
            }
        })

    }

    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        locationHelper.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    open fun turnOnLocation(){ locationHelper.checkLocationSettings()}

    open fun updatedGpsStatus(statusOfGPS: Boolean) {}

    open fun updatedLocation(it: Location?) {}



    // Permission check functions
    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>,
            grantResults: IntArray
    ) {
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // redirects to utils
    }

}