package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import FusedLocation
import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.REQUEST_CODE
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.parquesList
import kotlin.math.pow
import kotlin.math.sqrt


class ParkMeNowFragment : PermissionedFragment(REQUEST_CODE) {

    private var lastLocation: Location? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_park_me_now, container, false)
        val map = getParkMeNow()
        if (map != null) {
            startActivity(map)
        } else {
            val text = R.string.ligar_localizacao
            val duration = Toast.LENGTH_LONG
            val toast = Toast.makeText(activity as Context, text, duration)
            toast.show()
            //NavigationManager.goToHomeFragment(supportFragmentManager)
        }


        return view
    }

    override fun onStart() {
        super.onStart()
    }


    override fun onRequestPermissionsFailure() {
        val text = R.string.ligar_localizacao
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(activity as Context, text, duration)
        toast.show()
        //NavigationManager.goToHomeFragment(supportFragmentManager)
    }

    override fun onRequestPermissionsSucess() {
        FusedLocation.start(activity as Context)
    }

    override fun onDestroy() {
        super.onDestroy()
        //bottom_navigation.menu.findItem(R.id.nav_parkmenow).isChecked = false

    }
    override fun onPause() {
        super.onPause()
        activity?.supportFragmentManager?.let { NavigationManager.goToHomeFragment(it) }
    }

    /*override fun onResume() {
        activity?.supportFragmentManager?.let { NavigationManager.goToHomeFragment(it) }
        /*bottom_navigation.menu.findItem(R.id.nav_home).isChecked = true
        bottom_navigation.menu.findItem(R.id.nav_parkmenow).isChecked = false*/
        super.onResume()
    }*/


    private fun getParkMeNow(): Intent? {
        super.onRequestPermissions(
            activity?.baseContext!!,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
        if (isLocationEnabled(activity as Context)) {
            lastLocation = FusedLocation.getlastLocation()?.lastLocation
            var parquemaisPerto: Parque = parquesList.get(0)
            var distanciaMinima = 99999999999.0
            parquesList.forEach {
                val distanciaTemp = sqrt(
                    (it.latitude - lastLocation!!.latitude).pow(2) + (it.longitude - lastLocation!!.longitude).pow(2)
                )
                if (distanciaTemp <= distanciaMinima) {
                    parquemaisPerto = it
                    distanciaMinima = distanciaTemp
                }
            }
            val url =
                "http://maps.google.com/maps?daddr=" + parquemaisPerto.latitude + "," + parquemaisPerto.longitude + " (" + parquemaisPerto.nome + ")"
            val mapa = Intent(Intent.ACTION_VIEW)
            mapa.data = Uri.parse(url)
            return mapa
        } else {
            return null
        }
    }


    fun isLocationEnabled(context: Context): Boolean {
        var locationMode = 0

        try {
            locationMode = Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.LOCATION_MODE
            )
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF
    }
}



