package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.view.*
import kotlinx.android.synthetic.main.fragment_parqueinfo.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.parqueClicado

class InfoParqueFragment : Fragment(), OnMapReadyCallback {

    var map: GoogleMap? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_parqueinfo, container, false)
        view.map_view.onCreate(savedInstanceState)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mostrarDetalhes(parqueClicado)
        localizacaoBotao.setOnClickListener {
            val url =
                "http://maps.google.com/maps?daddr=" + parqueClicado.latitude + "," + parqueClicado.longitude + " (" + parqueClicado.nome + ")"
            val mapa = Intent(Intent.ACTION_VIEW)

            mapa.data = Uri.parse(url)
            startActivity(mapa)

        }

    }

    override fun onStart() {
        map_view.getMapAsync(this)
        map_view.onResume()
        super.onStart()

    }

    fun mostrarDetalhes(parque: Parque) {
        parque.let {
            nomeParquexml.text = parque.nome
            tipo_output.text = parque.tipo
            ocupacao_output.text = parque.ocupacao.toString()
            distancia_output.text = parque.distancia.toString() + " km"
            lastupdated_output.text = parque.data_ocupacao
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        this.map = map
        map?.addMarker(
            MarkerOptions()
                .position(LatLng(parqueClicado.latitude, parqueClicado.longitude))

        )
        map?.isBuildingsEnabled = true
        map?.animateCamera(
            CameraUpdateFactory
                .newLatLngZoom(
                    LatLng(
                        parqueClicado.latitude,
                        parqueClicado.longitude
                    ), 15F
                )


        )
    }

}