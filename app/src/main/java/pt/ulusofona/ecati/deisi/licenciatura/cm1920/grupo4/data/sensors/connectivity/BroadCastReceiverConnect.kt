
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class BroadCastReceiverConnect : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {

            val noConnectivity = intent.getBooleanExtra(
                ConnectivityManager.EXTRA_NO_CONNECTIVITY, false
            )

            if (noConnectivity) {
                Toast.makeText(context, "Disconnected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show()
            }
        }


    }
}