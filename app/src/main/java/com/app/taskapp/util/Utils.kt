package com.app.taskapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.app.taskapp.app.App
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    val STATUS_SUCCESS = 1

    fun isOnline(): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false
        val cm =
            App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                val netInfo = cm.activeNetworkInfo
                if (netInfo != null) {
                    return (netInfo.isConnected() && (netInfo.getType() == ConnectivityManager.TYPE_WIFI || netInfo.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                val netInfo = cm.activeNetwork
                if (netInfo != null) {
                    val nc = cm.getNetworkCapabilities(netInfo);

                    return (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc!!.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI
                    ));
                }
            }
        }

        return haveConnectedWifi || haveConnectedMobile
    }

    fun convertDate(dateInMilliseconds: Long): String? {
        val date = Date(dateInMilliseconds * 1000L)
        val format: DateFormat = SimpleDateFormat("dd MMM")
        format.timeZone = TimeZone.getTimeZone("Etc/UTC")
        val formatted = format.format(date)
        return formatted
    }

    fun nullCheck(int: String):String{
        return if (int == "null"){
            "0"
        } else {
            int
        }
    }
}