package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.parque_view_list.view.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque

class ParqueAdapter(private var dataSource: List<Parque>) :
    RecyclerView.Adapter<ParqueAdapter.ParqueViewHolder>(){

    lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParqueViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.parque_view_list, parent, false)
        return ParqueViewHolder(
            itemView,
            mListener
        )

    }



    override fun onBindViewHolder(holder: ParqueViewHolder, position: Int) {
        val currentItem = dataSource[position]
        if(currentItem.tipo.equals("Estrutura") ||  currentItem.tipo.equals("Structure") ){
            holder.imageView.setImageResource(R.drawable.parque_estrutura_icon)
        }else{
            holder.imageView.setImageResource(R.drawable.parque_superficie_icon)
        }
        holder.titleTextView.text = currentItem.nome
        holder.description.text = currentItem.tipo
        if(currentItem.percentagem>99.9 || currentItem.capacidade_max.toDouble()==0.0){
            holder.details.setImageResource(R.drawable.red)
            holder.ocupacao?.text = "Lotado"
        }else if(currentItem.percentagem in 90.0..99.9){
            holder.details.setImageResource(R.drawable.yellow)
            holder.ocupacao?.text = "Potencialmente Lotado"
        }else{
            holder.details.setImageResource(R.drawable.green)
            holder.ocupacao?.text = "Livre"
        }
        holder.percentagem.text = currentItem.percentagem.toString() + "%"


        //landscape

        //holder.ultimaEntrada?.text = currentItem.data_ocupacao
        holder.distancia?.text = (currentItem.distancia.toString() + " km")

    }

    override fun getItemCount() = dataSource.size

    class ParqueViewHolder(itemView: View, listener: OnItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.image_parque
        val titleTextView: TextView = itemView.nameparque_id
        val description: TextView = itemView.tipo
        val details: ImageView = itemView.image_led
        val percentagem: TextView = itemView.percentagem
        val ocupacao: TextView? = itemView.lotacao_text


        //landscape
        //var ultimaEntrada: TextView? = itemView.ultima_entrada
        val distancia: TextView? = itemView.distancia

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


    /*override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(
                constraint: CharSequence,
                results: FilterResults
            ) {
                list = results.values as List<E?>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence): FilterResults {
                var filteredResults: List<E?>? = null
                if (constraint.length == 0) {
                    filteredResults = originalList
                } else {
                    filteredResults = getFilteredResults(constraint.toString().toLowerCase())
                }
                val results = FilterResults()
                results.values = filteredResults
                return results
            }
        }
    }*/

}
