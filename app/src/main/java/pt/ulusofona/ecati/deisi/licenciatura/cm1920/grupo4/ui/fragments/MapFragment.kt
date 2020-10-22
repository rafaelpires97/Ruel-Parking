package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
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
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.parquesList


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    var map: GoogleMap? = null
    var marker: Marker? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        view.map_view.onCreate(savedInstanceState)

        return view

    }

    override fun onStart() {
        super.onStart()
        map_view.getMapAsync(this)
        map_view.onResume()
    }


    override fun onMapReady(mapa: GoogleMap?) {
        parquesList = AppDataBase.getDatabase(activity as Context).parqueDAO().getAll()
        this.map = mapa
        var location: LatLng

        parquesList.forEach {
            location = LatLng(it.latitude, it.longitude)
            val icon = getColor(it)
            map?.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(it.nome)
                    .icon(icon)
                    .snippet(getString(R.string.lugares_ocupados)+" " + it.ocupacao + "\n" + getString(R.string.lugares_existentes)+" " + it.capacidade_max + "\n" + getString(R.string.ultimaEntrada) + it.data_ocupacao+" " + "\n" + getString(R.string.click_to_go))

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
            map?.moveCamera(CameraUpdateFactory.newLatLng(location))
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 12F))
        }
        map?.setOnInfoWindowClickListener(this)

    }

    override fun onInfoWindowClick(marker: Marker?) {
        val toast = Toast.makeText(activity as Context, getString(R.string.clicked), Toast.LENGTH_LONG)
        toast.show()
        marker?.position
        val latitude = marker?.position!!.latitude
        val longitude = marker?.position!!.longitude
        val url =
            "http://maps.google.com/maps?daddr=" + latitude + "," + longitude + " (" + marker.title + ")"
        val mapa = Intent(Intent.ACTION_VIEW)

        mapa.data = Uri.parse(url)
        startActivity(mapa)
    }

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
    }


}