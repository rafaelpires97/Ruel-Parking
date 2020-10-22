package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.sensors.battery

import android.content.Context
import android.os.BatteryManager
import android.os.Handler

class Battery private constructor(private val context: Context): Runnable{

    private val TAG = Battery::class.java.simpleName

    private val TIME_BETWEEN_UPDATES = 2000L

    companion object{
        private var instance: Battery? = null
        private val handler = Handler()
        private var listener: OnBatteryCurrentListener? = null

        fun start(context: Context) {
            instance = if(instance == null) Battery(context) else instance
            instance?.start()
        }

        fun registerListener ( listener: OnBatteryCurrentListener){

            this.listener = listener
        }

        fun unregisterListener(){
            this.listener = null
        }

        fun notifyListeners(currentBattery : Double){
            listener?.onCurrentChanged(currentBattery)
        }


    }

    fun start(){
        handler.postDelayed(this,TIME_BETWEEN_UPDATES)
    }

    fun getBatteryCurrentNow(): Double {
        val manager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

        val value = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        return if(value != 0 && value != Int.MIN_VALUE) value.toDouble() / 1000000 else 0.0
    }

    override fun run(){
        val current = getBatteryCurrentNow()

        handler.postDelayed(this, TIME_BETWEEN_UPDATES)
        notifyListeners(current)
    }

}