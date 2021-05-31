package com.example.locationdemo

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.locationdemo.location.BaseLocationActivity

class MainActivity : BaseLocationActivity() {
    private var recyclerView:RecyclerView?=null
    private var adapter:LocationUpdatesAdaptor?=null
    private var lastLocation:Location?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        adapter = LocationUpdatesAdaptor(arrayListOf(),this)
        recyclerView!!.addItemDecoration(
                DividerItemDecoration(
                        recyclerView!!.context,
                        (recyclerView!!.layoutManager as LinearLayoutManager).orientation
                )
        )
        recyclerView!!.adapter = adapter
        findViewById<Button>(R.id.bt_turn_on_gps).setOnClickListener {
            turnOnLocation()
            findViewById<RelativeLayout>(R.id.rl_gps_status).visibility= View.GONE
        }
    }

    override fun updatedGpsStatus(statusOfGPS: Boolean) {
        findViewById<RelativeLayout>(R.id.rl_gps_status).visibility=(if(statusOfGPS){
            View.GONE}else{View.VISIBLE})
    }

    override fun updatedLocation(it: Location?) {
        if(it!=null) {
         if(lastLocation!=null) {
              if(it.latitude!=lastLocation!!.latitude && it.longitude!=lastLocation!!.longitude) {
                  lastLocation=it
                  adapter!!.addCustomerData(it!!)
              }
           }else {
               lastLocation=it
               adapter!!.addCustomerData(it!!)
         }

        }
    }
}