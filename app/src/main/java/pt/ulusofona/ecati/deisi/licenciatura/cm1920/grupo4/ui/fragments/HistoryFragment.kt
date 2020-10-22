package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_historico.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Historico
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.listHistorico
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter.HistoricoAdapter

class HistoryFragment : Fragment() {

    lateinit var mAdapter: HistoricoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_historico, container, false)

        return view
    }


    override fun onStart() {
        buildRecyclerView()


        button_add.setOnClickListener {
            activity?.supportFragmentManager?.let { NavigationManager.goToHistoricoAddFragment(it) }
        }


        super.onStart()
    }

    fun buildRecyclerView() {
        var precoTotal = 0.0
        var tempoTotal = 0
        listHistorico = AppDataBase.getDatabase(requireContext()).historicoDAO().getList()

        listHistorico.forEach {
            precoTotal += it.precoHora*it.qtdHoras
            tempoTotal += it.qtdHoras
        }

        preco_output.text = precoTotal.toString()
        tempo_output.text = tempoTotal.toString()


        mAdapter =
            HistoricoAdapter(
                listHistorico
            )
        recycler_view_historico.adapter = mAdapter
        recycler_view_historico.layoutManager = LinearLayoutManager(activity)
        recycler_view_historico.setHasFixedSize(true)

        mAdapter.setOnItemClickListener(object : HistoricoAdapter.OntItemClickListener {
            override fun onItemClick(position: Int) {
                //activity?.supportFragmentManager?.let { NavigationManager.goToUpdateFragment(it) }
            }
        })

    }
}