package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.car_view_list.view.imageCar
import kotlinx.android.synthetic.main.historico_view_list.view.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Historico
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments.UpdateFragment

class HistoricoAdapter(private val dataSource: List<Historico>) :
    RecyclerView.Adapter<HistoricoAdapter.HistoricoViewHolder>() {


    lateinit var mListener: OntItemClickListener

    interface OntItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OntItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.historico_view_list, parent, false)
        return HistoricoViewHolder(
            itemView,
            mListener
        )
    }



    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        val currentItem = dataSource[position]
        var precoHora = currentItem.precoHora
        var qtdHoras  = currentItem.qtdHoras
        var precoFinal : Double = precoHora * qtdHoras
        holder.preco.text = "${currentItem.precoHora}"+ "€/H"
        holder.matricula.text = currentItem.veiculoHistoricoMatricula
        //holder.nomeCarro.text = currentItem.veiculoHistoricoNome
        //holder.tipo.text = currentItem.parqueHistoricoTipo
        holder.nomeParque.text = currentItem.parqueHistoricoName
        holder.precofinal.text = """${precoFinal}€"""
        holder.tempofinal.text = "${currentItem.qtdHoras}" + " h"
        if(!dataSource[position].image_parque.equals("")){
            holder.imageCarro.setImageBitmap(UpdateFragment.openFile(dataSource[position].image_parque))
        }else{
            holder.imageCarro.setImageResource(R.drawable.car_logo)
        }


    }

    override fun getItemCount() = dataSource.size

    class HistoricoViewHolder(itemView: View, listener: OntItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        val nomeParque: TextView = itemView.text_parque
        val tipo: TextView = itemView.text_tipoinput
        val preco: TextView = itemView.text_precoinput
        val nomeCarro: TextView = itemView.text_carro
        val matricula: TextView = itemView.text_matricula
        val precofinal: TextView = itemView.text_preco
        val tempofinal: TextView = itemView.text_tempo
        val imageParque : ImageView = itemView.image_parque
        val imageCarro : ImageView = itemView.imageCar

        init {
            itemView.setOnClickListener {
                if (listener != null) {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position)
                    }
                }
            }

        }

    }


}


