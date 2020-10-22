
import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.sensors.location.OnLocationChangedListener

class FusedLocation private constructor(context: Context) : LocationCallback() {

    private val TAG = FusedLocation::class.java.simpleName

    private val TIME_BETWEEN_UPDATES = 5 * 1000L

    private var locationRequest: LocationRequest? = null


    private var client = FusedLocationProviderClient(context)

    init {
        locationRequest = LocationRequest()

        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = TIME_BETWEEN_UPDATES

        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest!!)
            .build()

        LocationServices.getSettingsClient(context)
            .checkLocationSettings(locationSettingsRequest)


    }

    companion object {
        private var listener: OnLocationChangedListener? = null
        private var instance: FusedLocation? = null
        private var lastLocation: LocationResult? = null


        fun registerListener(listener: OnLocationChangedListener) {
            this.listener = listener
        }

        fun unregisterListener() {
            listener = null
        }

        fun notifyListeners(locationResult: LocationResult) {
            listener?.onLocationChanged(locationResult)
        }

        fun start(context: Context) {
            instance = if (instance == null) FusedLocation(context) else instance
            instance?.startLocationUpdates()
        }

        fun getlastLocation(): LocationResult? {
            return lastLocation
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        client.requestLocationUpdates(locationRequest, this, Looper.myLooper())
    }

    override fun onLocationResult(locationResult: LocationResult) {
        Log.i(TAG, locationResult.lastLocation.toString())
        lastLocation= locationResult
        locationResult?.let { notifyListeners(it) }
        super.onLocationResult(locationResult)
    }


}