package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities

import ShakeListener
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Historico
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Veiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.sensors.acelerometer.OnShakeListener
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.sensors.battery.Battery
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.sensors.battery.OnBatteryCurrentListener
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.battery.BroadCastReceiverBattery
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.connectivity.BroadCastReceiverConnect
import java.text.SimpleDateFormat
import java.util.*


var parquesList = listOf<Parque>()
lateinit var parqueClicado: Parque
var listVeiculo = listOf<Veiculo>()
var listaSearch = mutableListOf<String>()
var listHistorico = listOf<Historico>()
var darkModeOn = false
var shakeOn = true
var SHARED_PREFS = "sharedPrefs"
const val REQUEST_CODE = 100


class MainActivity : AppCompatActivity(), OnBatteryCurrentListener {

    var batteryBroadCastReceiver: BroadCastReceiverBattery = BroadCastReceiverBattery()
    var broadcastReceiver: BroadCastReceiverConnect = BroadCastReceiverConnect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppDataBase.getDatabase(this@MainActivity).parqueDAO().insertAll(parquesList)


        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bottomNav = bottom_navigation
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        search_button.setOnClickListener {
            search_write.visibility = VISIBLE
            search_back.visibility = VISIBLE
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.select_dialog_item, listaSearch)
        //Getting the instance of AutoCompleteTextView
        //Getting the instance of AutoCompleteTextView
        val actv = findViewById<View>(R.id.search_write) as AutoCompleteTextView
        actv.threshold = 1 //will start working from first character

        actv.setAdapter(adapter) //setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.WHITE)



        search_back.setOnClickListener {
            search_write.visibility = GONE
            search_back.visibility = GONE
        }



        info.setOnClickListener {
            NavigationManager.goToHistoryFragment(
                supportFragmentManager
            )
        }

        bykes.setOnClickListener {
            NavigationManager.goToGiras(
                supportFragmentManager
            )
        }

        if (!screenRotated(savedInstanceState)) {
            NavigationManager.goToHomeFragment(supportFragmentManager)
        }
    }

    fun registenerListener(onBatteryCurrentListener: OnBatteryCurrentListener) {
        Battery.start(this)
        Battery.registerListener(this)
    }

    private fun startFilters() {
        setFilterFalse()

    }


    override fun onStart() {
        super.onStart()
        shake()
        startFilters()
        onCurrentChanged(2.0)
        registenerListener(this)
        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        search_write.onItemClickListener =
            AdapterView.OnItemClickListener() { adapterView, view, i, l ->


                val nome: String = adapterView.getItemAtPosition(i) as String

                parquesList.forEach {
                    if (nome.equals(it.nome)) {
                        parqueClicado = it
                    }
                }

                search_write.visibility = GONE
                search_back.visibility = GONE
                val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                search_write.setText("")
                supportFragmentManager.let { NavigationManager.goToInfoVeiculoFragment(it) }
            }
        registerReceiver(batteryBroadCastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
        unregisterReceiver(batteryBroadCastReceiver)

    }


    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }

    private val navListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.nav_home -> {
                    NavigationManager.goToHomeFragment(
                        supportFragmentManager
                    )
                }

                R.id.nav_definition -> {
                    NavigationManager.goToDefinitionFragment(
                        supportFragmentManager
                    )
                }
                R.id.nav_veiculos -> {
                    NavigationManager.goToVeiculoFragment(
                        supportFragmentManager
                    )
                }
                R.id.nav_parkmenow -> {
                    NavigationManager.goToParkMeNowFragment(
                        supportFragmentManager
                    )
                    item.isCheckable = false

                }

                R.id.nav_map -> {
                    NavigationManager.goToMapFragment(supportFragmentManager)
                }
            }

            true
        }

    override fun onCurrentChanged(current: Double) {
        Log.i("bateria", current.toString())
        val format = SimpleDateFormat("HH")
        var hora = format.format(Date()).toInt()


        val darkModeContexto = getSharedPreferences("ruel", 0)!!.getBoolean("darkmodeContexto", true)

        if(darkModeContexto){
            if ((hora in 19..24 || hora in 0..8)) {
                Log.i("Horas", "$hora")
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                if ((hora in 9..18 )) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }


        /*if(!darkModeOn && hora in 9..18){
            Log.i("deu","bosta")
            darkModeOn = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }*/

    }

    fun shake() {
        val shaker = ShakeListener(this)
        shaker.setOnShakeListener(object : OnShakeListener {
            override fun onShake() {
                Log.i("abanei", "ecra")
                shakeOn = getSharedPreferences("ruel", 0)!!.getBoolean("shake", true)
                if (shakeOn) {
                    setFilterFalse()
                    Toast.makeText(baseContext, "Filtros Resetados", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    fun setFilterFalse() {
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("estrutura", false).apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("superficie", false).apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("menos5", false).apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("entre5-10", false).apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("mais10", false).apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("menos90", false).apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("entre90-99", false).apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("100", false).apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("decrescenteDistancia", false)
            .apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("crescenteDistancia", false)
            .apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("decrescenteOcupacao", false)
            .apply()
        this.getSharedPreferences("ruel", 0)!!.edit().putBoolean("crescenteOcupacao", false).apply()


    }


}
