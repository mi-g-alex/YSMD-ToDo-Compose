package by.g_alex.ysmd_todo_compose.common.broadcast_receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class NetworkChangeReceiver(private val listener: NetworkChangeListener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val cm = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        val isConnected = netInfo != null && netInfo.isConnected
        listener.onNetworkChanged(isConnected)
    }

    interface NetworkChangeListener {
        fun onNetworkChanged(isConnected: Boolean)
    }
}