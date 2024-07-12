package by.g_alex.ysmd_todo_compose.common.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities

fun networkCallback(available: (Boolean) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            available(false)
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            available(true)
        }
    }
}

fun getNetworkConnectionState(connectivityManager: ConnectivityManager): Boolean =
    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
