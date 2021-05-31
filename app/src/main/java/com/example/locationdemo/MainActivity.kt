package com.example.locationdemo

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.locationdemo.location.BaseLocationActivity

class MainActivity : BaseLocationActivity() {
    private var recyclerView:RecyclerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.bt_turn_on_gps).setOnClickListener {
            turnOnLocation()
            findViewById<RelativeLayout>(R.id.rl_gps_status).visibility= View.GONE
            findViewById<ConstraintLayout>(R.id.cl_progressbar).visibility=View.VISIBLE
        }
    }

    override fun updatedGpsStatus(statusOfGPS: Boolean) {
        findViewById<RelativeLayout>(R.id.rl_gps_status).visibility=(if(statusOfGPS){
            View.GONE}else{View.VISIBLE})
    }

    override fun updatedLocation(it: Location?) {
        if(it!=null) {
            findViewById<ConstraintLayout>(R.id.cl_progressbar).visibility=View.GONE
            findViewById<TextView>(R.id.tv_accuracy_value).text = "${it.accuracy}"
            findViewById<TextView>(R.id.tv_latitude_value).text = "${it.latitude}"
            findViewById<TextView>(R.id.tv_longitude_value).text = "${it.longitude}"
            findViewById<TextView>(R.id.tv_altitude_value).text = "${it.altitude}"
        }
    }
}