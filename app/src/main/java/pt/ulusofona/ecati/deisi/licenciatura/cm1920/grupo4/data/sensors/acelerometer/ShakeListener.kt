import android.content.Context
import android.hardware.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.sensors.acelerometer.OnShakeListener

class ShakeListener(context: Context?) : SensorEventListener {
    private var mSensorMgr: SensorManager? = null
    private var mLastX = -1.0f
    private var mLastY = -1.0f
    private var mLastZ = -1.0f
    private var mLastTime: Long = 0
    private var mShakeListener: OnShakeListener? = null
    private var mShakeCount = 0
    private var mLastShake: Long = 0
    private var mLastForce: Long = 0
    private val context: Context

    companion object {
        private const val FORCE_THRESHOLD = 800
        private const val TIME_THRESHOLD = 100
        private const val SHAKE_TIMEOUT = 500
        private const val SHAKE_DURATION = 1000
        private const val SHAKE_COUNT = 5
    }

    init {
        this.context = context!!
        resume()
    }

    fun setOnShakeListener(listener: OnShakeListener?) {
        mShakeListener = listener
    }

    fun resume() {
        mSensorMgr = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (mSensorMgr == null) {
            throw UnsupportedOperationException("Sensors not supported")
        }
        val supported = mSensorMgr!!.registerListener(this,mSensorMgr!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME,SensorManager.SENSOR_DELAY_GAME)
        if (!supported) {
            mSensorMgr!!.unregisterListener(this)
            throw UnsupportedOperationException("Accelerometer not supported")
        }
    }

    fun pause() {
        if (mSensorMgr != null) {
            mSensorMgr!!.unregisterListener(this)
            mSensorMgr = null
        }
    }



    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(sensor: SensorEvent) {
        if (sensor.sensor.type != Sensor.TYPE_ACCELEROMETER) return
        val now = System.currentTimeMillis()
        if (now - mLastForce > SHAKE_TIMEOUT) {
            mShakeCount = 0
        }
        if (now - mLastTime > TIME_THRESHOLD) {
            val diff = now - mLastTime
            val speed = Math.abs(
                sensor.values[SensorManager.DATA_X] + sensor.values[SensorManager.DATA_Y] + sensor.values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ
            ) / diff * 10000
            if (speed > FORCE_THRESHOLD) {
                if (++mShakeCount >= SHAKE_COUNT && now - mLastShake > SHAKE_DURATION) {
                    mLastShake = now
                    mShakeCount = 0
                    if (mShakeListener != null) {
                        mShakeListener!!.onShake()
                    }
                }
                mLastForce = now
            }
            mLastTime = now
            mLastX = sensor.values[SensorManager.DATA_X]
            mLastY = sensor.values[SensorManager.DATA_Y]
            mLastZ = sensor.values[SensorManager.DATA_Z]
        }
    }
}