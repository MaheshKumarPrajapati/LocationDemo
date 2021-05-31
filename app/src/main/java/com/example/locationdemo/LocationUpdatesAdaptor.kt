package com.example.locationdemo

import android.content.Context
import android.location.Location
import android.os.Build
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class LocationUpdatesAdaptor(
        private val customersList: ArrayList<Location>,
        private val context: Context
) : RecyclerView.Adapter<LocationUpdatesAdaptor.CustomerDataViewHolder>(){


    class CustomerDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
                list: Location,
                context: Context

        ) {



            itemView.findViewById<TextView>(R.id.tv_latitute).text =   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml("<b>Latitude:</b> ${list.latitude}", Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("<b>Latitude:</b> ${list.latitude}")
            }
            itemView.findViewById<TextView>(R.id.tv_longitude).text =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml("<b>Longitude:</b> ${list.longitude}", Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("<b>Longitude:</b> ${list.longitude}")
            }
            itemView.findViewById<TextView>(R.id.tv_altitude).text =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml("<b>Altitude:</b> ${list.altitude}", Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("<b>Altitude:</b> ${list.altitude}")
            }
            itemView.findViewById<TextView>(R.id.tv_accuracy).text= if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml("<b>Accuracy:</b> ${list.accuracy}", Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("<b>Accuracy:</b> ${list.accuracy}")
            }
            itemView.findViewById<TextView>(R.id.tv_baring).text =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml("<b>Bearing:</b> ${list.bearing}", Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("<b>Bearing:</b> ${list.bearing}")
            }
            itemView.findViewById<TextView>(R.id.tv_speed).text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml("<b>Speed:</b> ${list.speed}", Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml("<b>Speed:</b> ${list.speed}")
            }

            getTimeInterval(itemView,list.time)
        }

        private fun getTimeInterval(itemView: View, time: Long) {
            try {
                val now = Date()
                val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - time)
                val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - time)
                val hours: Long = TimeUnit.MILLISECONDS.toHours(now.getTime() - time)
                val days: Long = TimeUnit.MILLISECONDS.toDays(now.getTime() - time)

                if (seconds < 60) {
                    itemView.findViewById<TextView>(R.id.tv_time).setText("Location update $seconds seconds ago")
                    Log.d("TIMECHECK","Location update $seconds seconds ago")
                } else if (minutes < 60) {
                    itemView.findViewById<TextView>(R.id.tv_time).setText("Location update $minutes minutes ago")
                } else if (hours < 24) {
                    itemView.findViewById<TextView>(R.id.tv_time).setText("Location update $hours hours ago")
                } else {
                    itemView.findViewById<TextView>(R.id.tv_time).setText("Location update $days days ago")
                }
            } catch (j: Exception) {
                j.printStackTrace()
                Log.d("TIMECHECK","Exception "+j.message)
            }
        }
    }

    val list:ArrayList<Location> =ArrayList<Location>()
    fun addCustomerData(item:Location) {
        list.add(item)
        this.customersList.apply {
            clear()
            addAll(list)
        }
        customersList.reverse()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerDataViewHolder =
            CustomerDataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.location_update_items, parent, false))

    override fun getItemCount(): Int = customersList.size

    override fun onBindViewHolder(holder: CustomerDataViewHolder, position: Int) {
        if(customersList.size>position){
            holder.bind(customersList[position], context)
        }
    }





}