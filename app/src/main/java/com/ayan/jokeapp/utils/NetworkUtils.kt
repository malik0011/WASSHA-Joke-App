package com.ayan.jokeapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

class NetworkUtils {

    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork
                val capabilities =
                    connectivityManager.getNetworkCapabilities(network)
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                networkInfo != null && networkInfo.isConnected
            }
        }
    }
}