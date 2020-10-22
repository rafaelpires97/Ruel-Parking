package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_insert_historico.*
import kotlinx.android.synthetic.main.fragment_insert_veiculo.data_info_car
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Historico
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.listHistorico
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.listVeiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.parquesList
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter.HistoricoAdapter


lateinit var newHistorico : Historico
class HistoricoAddFragment : Fragment() {

    lateinit var mAdapter: HistoricoAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_insert_historico, container, false)

        return view
    }



    var i: Int = 0
    var count = -1
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        var arrayParque = mutableListOf<String>()
        var arrayVeiculo = mutableListOf<String>()
        parquesList.forEach{
            arrayParque.add(it.nome)
        }
        listVeiculo.forEach {
            arrayVeiculo.add(it.marca+ " " + it.modelo+ " (" + it.matricula+")")
        }


        val adapterCarro: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayVeiculo
        )
        adapterCarro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        veiculoSpinner.adapter = adapterCarro

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item, arrayParque
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        parqueSpinner.adapter = adapter

        count++
        var path : String = ""
        listVeiculo.forEach {
            if((it.marca+ " " + it.modelo+ " (" + it.matricula+")").equals(veiculoSpinner.selectedItem.toString())){
                path=it.image_carro
            }
        }

        btn_history_insert.setOnClickListener {
            newHistorico = Historico(
                count,
                parqueSpinner.selectedItem.toString(),
                veiculoSpinner.selectedItem.toString(),
                preco_info_car.text.toString().toDouble(),
                horas_car.text.toString().toInt(),
                data_info_car.text.toString(),
                path
            )


            mAdapter = HistoricoAdapter(listHistorico)
            mAdapter.notifyItemChanged(i)

            insert(listHistorico.size, newHistorico)



            activity?.supportFragmentManager?.let { NavigationManager.goToHistoryFragment(it) }

        }
    }

    fun insert(position: Int, historico: Historico) {
        AppDataBase.getDatabase(activity as Context).historicoDAO().save(historico)
        mAdapter.notifyItemInserted(position)
    }
}