package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.battery


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.darkModeOn


class BroadCastReceiverBattery : BroadcastReceiver() {

    var count = 0
    override fun onReceive(context: Context?, intent: Intent) {

        val level = intent.getIntExtra("level", 0)


        if (level < 21 && count == 0) {

            count++
            if (darkModeOn == false) {
                AlertDialog.Builder(context!!)
                    .setTitle(R.string.bateria_fraca)
                    .setMessage(R.string.mudarparaNight)
                    .setPositiveButton(R.string.sim) { dialog, which ->
                        darkModeOn = true // trocar para shared preferences
                        context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("darkmode", true).apply()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    .setNegativeButton(R.string.nao) { dialog , which ->
                        darkModeOn = false // trocar para shared preferences
                        context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("darkmode", false).apply()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    .setIcon(R.drawable.ic_baseline_battery_alert)
                    .show()
            }

        }

    }

}