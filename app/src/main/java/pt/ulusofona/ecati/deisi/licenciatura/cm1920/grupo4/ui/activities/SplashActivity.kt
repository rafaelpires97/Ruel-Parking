package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activity


import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.remote.RetrofitClient
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.remote.service.ParqueService
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.MainActivity
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.listaSearch
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.parquesList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    var mDelayHandler: Handler? = null
    val SPLASH_TIMER: Long = 3000 // 3 segundos
    private var lastLocation: Location? = null

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(
                applicationContext,
                MainActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mDelayHandler = Handler()
        getData()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_TIMER)

    }


    private fun getData() {
        val token = "93600bb4e7fee17750ae478c22182dda"
        val retrofitClient = RetrofitClient.getRetrofitInstance()
        val endpoint = retrofitClient.create(ParqueService::class.java)
        val callback = endpoint.getParkingLots(token)
        callback.enqueue(object : Callback<List<Parque>> {
            override fun onFailure(call: Call<List<Parque>>?, t: Throwable?) {
                if (t != null) {
                    Toast.makeText(baseContext, R.string.sem_ligacao, Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call<List<Parque>>?, response: Response<List<Parque>>?) {
                if (response != null) {
                    parquesList = response.body()!! as MutableList<Parque>
                    parquesList.forEach {
                        if (it.tipo.equals("Estrutura")) {
                            it.tipo = getString(R.string.estrutura)
                        } else {
                            it.tipo = getString(R.string.superficie)
                        }
                        it.percentagem =
                            ((it.ocupacao.toDouble() / it.capacidade_max.toDouble()) * 100).toInt()
                        listaSearch.add(it.nome)
                    }
                }
            }
        })
    }
}