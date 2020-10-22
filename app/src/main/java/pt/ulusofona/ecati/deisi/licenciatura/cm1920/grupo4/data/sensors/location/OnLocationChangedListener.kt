package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.sensors.location

import com.google.android.gms.location.LocationResult

interface OnLocationChangedListener {
    fun onLocationChanged (locationResult : LocationResult)


}