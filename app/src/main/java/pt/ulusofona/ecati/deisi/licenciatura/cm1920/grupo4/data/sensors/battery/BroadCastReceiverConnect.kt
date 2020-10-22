package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R

class BroadCastReceiverConnect : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {

            val noConnectivity = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
            )

            if (noConnectivity) {
                Toast.makeText(context, R.string.disconectado, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, R.string.conectado, Toast.LENGTH_SHORT).show()
            }
        }


    }
}