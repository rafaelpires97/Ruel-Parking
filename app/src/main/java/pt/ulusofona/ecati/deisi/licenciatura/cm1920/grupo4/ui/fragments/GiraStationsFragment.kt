package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo3.data.remote.responses.GiraStation.GiraStation
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.GiraModel
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.remote.RetrofitClient
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.remote.service.BykeService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GiraStationsFragment : Fragment(), OnMapReadyCallback {

    var map: GoogleMap? = null
    var gira: GiraStation? = null
    var giraList = mutableListOf<GiraModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        view.map_view.onCreate(savedInstanceState)
        getData()
        return view

    }

    fun getData() {
        val token = "93600bb4e7fee17750ae478c22182dda"
        val retrofitClient = RetrofitClient.getRetrofitInstance()
        val endpoint = retrofitClient.create(BykeService::class.java)
        val callback = endpoint.getBike(token)
        callback.enqueue(object : Callback<GiraStation> {
            override fun onFailure(call: Call<GiraStation>?, t: Throwable?) {
                if (t != null) {
                    Toast.makeText(activity as Context, t.message, Toast.LENGTH_SHORT).show()
                    giraList = AppDataBase.getDatabase(activity as Context).giraDAO()
                        .getList() as MutableList
                }
            }

            override fun onResponse(call: Call<GiraStation>?, response: Response<GiraStation>?) {
                if (response != null) {
                    gira = response.body() as GiraStation
                    gira!!.features.forEach {
                        giraList.add(
                            GiraModel(
                                it.properties.id_expl,
                                it.properties.desig_comercial,
                                it.geometry.coordinates[0][0],
                                it.geometry.coordinates[0][1],
                                it.properties.num_bicicletas,
                                it.properties.num_docas,
                                it.properties.update_date
                            )
                        )
                    }

                    var location: LatLng
                    giraList.forEach {
                        location = LatLng(it.latitude, it.longitude)
                        map?.addMarker(
                            MarkerOptions()
                                .position(location)
                                .title(it.nome)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bike))
                                .snippet(
                                    getString(R.string.num_bikes) + " " + it.numBike + "\n" + getString(
                                        R.string.num_docas
                                    ) + " " + it.numDocas + "\n" + getString(R.string.ultimaEntrada) + " " + it.ultimoUpdate
                                )

                        )
                        map?.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
                            override fun getInfoWindow(arg0: Marker?): View? {
                                return null
                            }

                            override fun getInfoContents(marker: Marker): View? {
                                val info = LinearLayout(context)
                                info.orientation = LinearLayout.VERTICAL
                                val title = TextView(context)
                                title.setTextColor(context!!.getColor(R.color.mapa))
                                title.gravity = Gravity.CENTER
                                title.setTypeface(null, Typeface.BOLD)
                                title.text = marker.title
                                val snippet = TextView(context)
                                snippet.setTextColor(context!!.getColor(R.color.mapa))
                                snippet.text = marker.snippet
                                snippet.gravity = Gravity.CENTER
                                info.addView(title)
                                info.addView(snippet)
                                return info
                            }
                        })

                        //map?.moveCamera(CameraUpdateFactory.newLatLng(location))
                        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12F))
                    }
                    AppDataBase.getDatabase(activity as Context).giraDAO()
                        .insertAll(giraList as List<GiraModel>)
                }
            }

        })

    }

    override fun onStart() {
        super.onStart()
        getData()
        map_view.getMapAsync(this)
        map_view.onResume()
    }

    override fun onMapReady(mapa: GoogleMap?) {
        //parquesList = ParqueDataBase.getDatabase(activity as Context).parqueDAO().getAll()
        this.map = mapa
        var location: LatLng

    }

    /*
    fun getColor(p: Parque): BitmapDescriptor {
        val percentagem = ((p.ocupacao.toDouble() / p.capacidade_max.toDouble()) * 100)
        if (p.tipo.equals("Estrutura")) {
            if (percentagem > 99.9 || p.capacidade_max.toDouble() == 0.0) {
                return BitmapDescriptorFactory.fromResource(R.drawable.estrutura_red)
            } else if (percentagem in 90.0..99.9) {
                return BitmapDescriptorFactory.fromResource(R.drawable.estrutura_yellow)
            } else {
                return BitmapDescriptorFactory.fromResource(R.drawable.estrutura_green)
            }
        } else {
            if (percentagem > 99.9 || p.capacidade_max.toDouble() == 0.0) {
                return BitmapDescriptorFactory.fromResource(R.drawable.superficie_red)
            } else if (percentagem in 90.0..99.9) {
                return BitmapDescriptorFactory.fromResource(R.drawable.superficie_yellow)
            } else {
                return BitmapDescriptorFactory.fromResource(R.drawable.superficie_green)
            }
        }
    }*/


}