package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import FusedLocation
import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationResult
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.runBlocking
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.sensors.location.OnLocationChangedListener
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.REQUEST_CODE
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.parqueClicado
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.parquesList
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapters.ParqueAdapter
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.viewmodels.HomeViewModel


class HomeFragment : PermissionedFragment(REQUEST_CODE), OnLocationChangedListener {

    lateinit var mAdapter: ParqueAdapter
    private lateinit var viewModelHome: HomeViewModel
    private var lastLocation: Location? = null
    var myDialog: Dialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        runBlocking {
            super.onRequestPermissions(
                activity?.baseContext!!,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
        myDialog = Dialog(activity as Context)
        viewModelHome = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return view

    }

/*
    fun shake() {
        val shaker = ShakeListener(context)
        shaker.setOnShakeListener(object : OnShakeListener {
            override fun onShake() {

                Toast.makeText(activity as Context, R.string.filter_reseted, Toast.LENGTH_LONG)

                //setFilterFalse()
                buildRecyclerView(parquesList)
            }

        })
    }*/

    /*fun setFilterFalse() {

        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("estrutura",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("superficie",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("menos5",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("entre5-10",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("mais10",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("menos90",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("entre90-99",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("100",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("decrescenteDistancia",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("crescenteDistancia",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("decrescenteOcupacao",false).apply()
        context?.getSharedPreferences("AppPref",0)!!.edit().putBoolean("crescenteOcupacao",false).apply()



        /*checkbox_estrutura.isChecked = false
        checkbox_superficie.isChecked = false
        checkbox_menos5.isChecked = false
        checkbox_entre5_10.isChecked = false
        checkbox_mais10.isChecked = false
        checkbox_menos90.isChecked = false
        checkbox_entre90_99.isChecked = false
        checkbox_100.isChecked = false
        checkbox_decrescente_distancia.isChecked = false
        checkbox_crescente_distancia.isChecked = false
        checkbox_decrescente_ocupacao.isChecked = false
        checkbox_crescente_ocupacao.isChecked = false*/
    }*/


    override fun onRequestPermissionsSucess() {
        context?.let {
            FusedLocation.start(it)
        }

    }

    override fun onRequestPermissionsFailure() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    activity?.supportFragmentManager?.let { NavigationManager.goToHomeFragment(it) }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onResume() {
        filter_fragment.visibility = INVISIBLE
        super.onResume()

    }

    override fun onStart() {
        getDistance()
        parquesList = AppDataBase.getDatabase(activity as Context).parqueDAO().getAll()
        buildRecyclerView(parquesList)

        super.onStart()




        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val parque = parquesList[viewHolder.adapterPosition]
                val url =
                    "http://maps.google.com/maps?daddr=" + parque.latitude + "," + parque.longitude + " (" + parque.nome + ")"
                val mapa = Intent(Intent.ACTION_VIEW)

                mapa.data = Uri.parse(url)
                startActivity(mapa)

            }

            override fun onChildDrawOver(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder?,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(activity as Context,c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(activity as Context,R.color.colorAccent))
                    .addActionIcon(R.drawable.ic_map)
                    .create()
                    .decorate()

                super.onChildDrawOver(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )


            }


        }).attachToRecyclerView(recycler_view)

        button_filter.setOnClickListener {
            filter_fragment.visibility = VISIBLE
            checkbox_estrutura.isChecked = context?.getSharedPreferences("ruel", 0)!!.getBoolean("estrutura", false)
            checkbox_superficie.isChecked = context?.getSharedPreferences("ruel", 0)!!.getBoolean("superficie", false)
            checkbox_menos5.isChecked = context?.getSharedPreferences("ruel", 0)!!.getBoolean("menos5", false)
            checkbox_entre5_10.isChecked = context?.getSharedPreferences("ruel", 0)!!.getBoolean("entre5-10", false)
            checkbox_mais10.isChecked = context?.getSharedPreferences("ruel", 0)!!.getBoolean("mais10", false)
            checkbox_menos90.isChecked = context?.getSharedPreferences("ruel", 0)!!.getBoolean("menos90", false)
            checkbox_entre90_99.isChecked=context?.getSharedPreferences("ruel", 0)!!.getBoolean("entre90-99", false)
            checkbox_100.isChecked=context?.getSharedPreferences("ruel", 0)!!.getBoolean("100", false)
           // checkbox_crescente_distancia.isChecked=context?.getSharedPreferences("ruel", 0)!!.getBoolean("decrescenteDistancia", false)
            //checkbox_crescente_ocupacao.isChecked=context?.getSharedPreferences("ruel", 0)!!.getBoolean("crescenteDistancia", false)
            //checkbox_decrescente_ocupacao.isChecked=context?.getSharedPreferences("ruel", 0)!!.getBoolean("decrescenteOcupacao", false)
            //checkbox_decrescente_distancia.isChecked=context?.getSharedPreferences("ruel", 0)!!.getBoolean("crescenteOcupacao", false)
    }

        button_close.setOnClickListener {
            filter_fragment.visibility = INVISIBLE
        }

        button_ok.setOnClickListener {
            var listaFiltrada = listOf<Parque>()
            val tipoFiltro = getTipo()
            var haTipoFiltro = false


            val distanciaFiltro = getKm()
            var haDistanciaFiltro = false
            val distanciaList = getDistanceFiltro(distanciaFiltro)
            val minDistancia = distanciaList[0]
            val maxDistancia = distanciaList[1]


            val ocupacaoFiltro = getPercentangem()
            var haOcupacaoFiltro = false
            val ocupacaoList = getOcupacaoFiltro(ocupacaoFiltro)
            val minOcupacao = ocupacaoList[0]
            val maxOcupacao = ocupacaoList[1]


            if (!getTipo().equals("")) {
                haTipoFiltro = true
            } else if (getTipo().equals("")) {
                haTipoFiltro = false
            }

            if (getKm() == 1 || getKm() == 2 || getKm() == 3) {
                haDistanciaFiltro = true
            } else if (getKm() == 4) {
                haDistanciaFiltro = false
            }

            if (getPercentangem() == 1 || getPercentangem() == 2 || getPercentangem() == 3) {
                haOcupacaoFiltro = true
            } else if (getPercentangem() == 4) {
                haOcupacaoFiltro = false
            }


            if ((checkbox_decrescente_distancia.isChecked || checkbox_crescente_distancia.isChecked) && (checkbox_decrescente_ocupacao.isChecked || checkbox_crescente_ocupacao.isChecked)) {
                //MANDA POPUP A DIZER QUE NAO PODE TER DUAS ORDENACOES ATIVAS
                Toast.makeText(
                    activity as Context,
                    R.string.duas_variaveis,
                    Toast.LENGTH_SHORT
                ).show()
            }


            if (haTipoFiltro && haDistanciaFiltro && haOcupacaoFiltro) {  // tres filtros activos
                when {
                    checkbox_crescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_Tipo_DistanceASCOcupacao(
                                tipoFiltro,
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                    checkbox_decrescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_Tipo_DistanceDESCOcupacao(
                                tipoFiltro,
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                    checkbox_crescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_Tipo_DistanceASCDistancia(
                                tipoFiltro,
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                    checkbox_decrescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_Tipo_DistanceDESCDistancia(
                                tipoFiltro,
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                    else -> {
                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_Tipo_Distance(
                                tipoFiltro,
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                }
            } else if (haTipoFiltro && haDistanciaFiltro && !haOcupacaoFiltro) { // 2 filtros activos tipo e distancia
                when {
                    checkbox_crescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistance_TipoASCOcupacao(tipoFiltro, minDistancia, maxDistancia)
                    }
                    checkbox_decrescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistance_TipoDESCOcupacao(tipoFiltro, minDistancia, maxDistancia)
                    }
                    checkbox_crescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistance_TipoASCDistancia(tipoFiltro, minDistancia, maxDistancia)
                    }
                    checkbox_decrescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistance_TipoDESCDistancia(tipoFiltro, minDistancia, maxDistancia)
                    }
                    else -> {
                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistance_Tipo(tipoFiltro, minDistancia, maxDistancia)
                    }
                }
            } else if (haTipoFiltro && !haDistanciaFiltro && haOcupacaoFiltro) {// 2 filtros activos tipo e ocupacao
                when {
                    checkbox_crescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_TipoASCOcupacao(tipoFiltro, minOcupacao, maxOcupacao)
                    }
                    checkbox_decrescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_TipoDESCOcupacao(tipoFiltro, minOcupacao, maxOcupacao)
                    }
                    checkbox_crescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_TipoASCDistancia(tipoFiltro, minOcupacao, maxOcupacao)
                    }
                    checkbox_decrescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_TipoDESCDistancia(tipoFiltro, minOcupacao, maxOcupacao)
                    }
                    else -> {
                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_Tipo(tipoFiltro, minOcupacao, maxOcupacao)
                    }
                }
            } else if (!haTipoFiltro && haDistanciaFiltro && haOcupacaoFiltro) {// 2 filtros activos distancia e ocupacao
                when {
                    checkbox_crescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_DistanceASCOcupacao(
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                    checkbox_decrescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_DistanceDESCOcupacao(
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                    checkbox_crescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_DistanceASCDistancia(
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                    checkbox_decrescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_DistanceDESCDistancia(
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                    else -> {
                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao_Distance(
                                minOcupacao,
                                maxOcupacao,
                                minDistancia,
                                maxDistancia
                            )
                    }
                }
            } else if (haTipoFiltro && !haDistanciaFiltro && !haOcupacaoFiltro) {  // 1 filtro activo tipo
                when {
                    checkbox_crescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getTipoASCOcupacao(tipoFiltro)
                    }
                    checkbox_decrescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getTipoDESCOcupacao(tipoFiltro)
                    }
                    checkbox_crescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getTipoASCDistancia(tipoFiltro)
                    }
                    checkbox_decrescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getTipoDESCDistancia(tipoFiltro)
                    }
                    else -> {
                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getTipo(tipoFiltro)
                    }
                }
            } else if (!haTipoFiltro && haDistanciaFiltro && !haOcupacaoFiltro) { // 1 filtro activo distancia
                when {
                    checkbox_crescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistanceASCOcupacao(minDistancia, maxDistancia)
                    }
                    checkbox_decrescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistanceDESCOcupacao(minDistancia, maxDistancia)
                    }
                    checkbox_crescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistanceASCDistancia(minDistancia, maxDistancia)
                    }
                    checkbox_decrescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistanceDESCDistancia(minDistancia, maxDistancia)
                    }
                    else -> {
                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getDistance(minDistancia, maxDistancia)
                    }
                }
            } else if (!haTipoFiltro && !haDistanciaFiltro && haOcupacaoFiltro) { // 1 filtro activo ocupacao
                when {
                    checkbox_crescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacaoASCOcupacao(minOcupacao, maxOcupacao)
                    }
                    checkbox_decrescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacaoDESCOcupacao(minOcupacao, maxOcupacao)
                    }
                    checkbox_crescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacaoASCDistancia(minOcupacao, maxOcupacao)
                    }
                    checkbox_decrescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacaoDESCDistancia(minOcupacao, maxOcupacao)
                    }
                    else -> {
                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .getOcupacao(minOcupacao, maxOcupacao)
                    }
                }
            } else {
                when {
                    checkbox_crescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .orderByTaxaOcupacaoASC()
                    }
                    checkbox_decrescente_ocupacao.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .orderByTaxaOcupacaoDESC()
                    }
                    checkbox_crescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .orderByDistanciaASC()
                    }
                    checkbox_decrescente_distancia.isChecked -> {

                        listaFiltrada = AppDataBase.getDatabase(activity as Context).parqueDAO()
                            .orderByDistanciaDESC()
                    }
                    else -> {
                        listaFiltrada =
                            AppDataBase.getDatabase(activity as Context).parqueDAO().getAll()
                    }
                }
            }


            buildRecyclerView(listaFiltrada)
            filter_fragment.visibility = INVISIBLE
        }


    }

    fun getDistanceFiltro(distanciaFiltro: Int): List<Double> {
        var min = 0.0
        var max = 0.0
        val listaFiltros = mutableListOf<Double>()
        when (distanciaFiltro) {
            1 -> {
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("menos5", true).apply()
                min = 0.0
                max = 5.0
            }
            2 -> {
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("entre5-10", true).apply()
                min = 5.0
                max = 10.0
            }
            3 -> {
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("mais10", true).apply()
                min = 10.0
                max = 50000.0
            }
        }
        listaFiltros.add(min)
        listaFiltros.add(max)
        return listaFiltros
    }

    fun getOcupacaoFiltro(distanciaFiltro: Int): List<Int> {
        var min = 0
        var max = 0
        val listaFiltros = mutableListOf<Int>()
        when (distanciaFiltro) {
            1 -> {
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("menos90", true)
                min = 0
                max = 90
            }
            2 -> {
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("entre90-99", true)
                min = 90
                max = 99
            }
            3 -> {
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("100", true)
                min = 99
                max = 100
            }
        }
        listaFiltros.add(min)
        listaFiltros.add(max)
        return listaFiltros
    }

    fun getTipo(): String {
        if (checkbox_estrutura.isChecked) {
            context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("estrutura", true)
            checkbox_superficie.isChecked = false
            return "Estrutura"
        } else if (checkbox_superficie.isChecked) {
            context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("superficie", true)
            checkbox_estrutura.isChecked = false
            return "Superfície"
        } else {
            checkbox_superficie.isChecked = false
            checkbox_estrutura.isChecked = false
            return ""
        }
    }

    fun getKm(): Int {
        if (checkbox_menos5.isChecked) {
            checkbox_entre5_10.isChecked = false
            checkbox_mais10.isChecked = false
            return 1
        } else if (checkbox_entre5_10.isChecked) {
            checkbox_menos5.isChecked = false
            checkbox_mais10.isChecked = false
            return 2
        } else if (checkbox_mais10.isChecked) {
            checkbox_menos5.isChecked = false
            checkbox_entre5_10.isChecked = false
            return 3
        } else {
            checkbox_menos5.isChecked = false
            checkbox_entre5_10.isChecked = false
            checkbox_mais10.isChecked = false
            return 4
        }
    }

    fun getPercentangem(): Int {
        if (checkbox_menos90.isChecked) {
            checkbox_entre90_99.isChecked = false
            checkbox_100.isChecked = false
            return 1
        } else if (checkbox_entre90_99.isChecked) {
            checkbox_menos90.isChecked = false
            checkbox_100.isChecked = false
            return 2
        } else if (checkbox_100.isChecked) {
            checkbox_menos90.isChecked = false
            checkbox_entre90_99.isChecked = false
            return 3
        } else {
            checkbox_menos90.isChecked = false
            checkbox_entre90_99.isChecked = false
            checkbox_100.isChecked = false
            return 4
        }
    }




    fun getDistance() {
        lastLocation = FusedLocation.getlastLocation()?.lastLocation

        parquesList.forEach {
            if (lastLocation != null) {
                val localizacaoParque = Location("Localizacao Parque")
                localizacaoParque.latitude = it.latitude
                localizacaoParque.longitude = it.longitude
                it.distancia = Math.round((lastLocation?.distanceTo(localizacaoParque)!! / 1000.0) * 100.0) / 100.00
            } else {
                it.distancia = 0.0
            }
        }
        AppDataBase.getDatabase(activity as Context).parqueDAO().insertAll(parquesList)

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


    fun buildRecyclerView(parques: List<Parque>) {
        mAdapter =
            ParqueAdapter(
                parques
            )
        recycler_view?.adapter = mAdapter
        recycler_view?.layoutManager = LinearLayoutManager(activity)
        recycler_view?.setHasFixedSize(true)



        mAdapter.setOnItemClickListener(object : ParqueAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                parqueClicado = parquesList[position]
                activity?.supportFragmentManager?.let {
                    NavigationManager.goToInfoVeiculoFragment(
                            it
                            )
                }
            }
        })
    }




    override fun onLocationChanged(locationResult: LocationResult) {
        //lastLocation=locationResult
    }


}