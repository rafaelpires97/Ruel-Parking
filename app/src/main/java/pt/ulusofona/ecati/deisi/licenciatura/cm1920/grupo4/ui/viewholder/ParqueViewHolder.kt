package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.listeners.ParqueListener

class ParqueViewHolder(itemView: View, private val listener: ParqueListener) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(parque: Parque) {

        // Obtém os elementos de interface
        val textName = itemView.findViewById<TextView>(R.id.nameparque_id)

        // Atribui valores
        textName.text = parque.nome

        // Atribui eventos
        textName.setOnClickListener {
            listener.onClick(parque.id_parque)
        }

        // Atribui eventos
        textName.setOnLongClickListener {
            listener.onDelete(parque.id_parque)
            true
        }

    }
}
