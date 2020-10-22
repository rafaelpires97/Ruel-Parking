package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.car_view_list.view.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Veiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments.UpdateFragment.Companion.openFile

class VeiculoAdapter(private var dataSource: List<Veiculo>) :
    RecyclerView.Adapter<VeiculoAdapter.VeiculoViewHolder>() {


    lateinit var mListener: OntItemClickListener

    interface OntItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OntItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeiculoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.car_view_list, parent, false)
        return VeiculoViewHolder(
            itemView,
            mListener
        )
    }

    override fun onBindViewHolder(holder: VeiculoViewHolder, position: Int) {
        val currentItem = dataSource[position]
        holder.imageView.setImageBitmap(openFile(dataSource[position].image_carro))
        holder.marca.text = currentItem.marca
        holder.matricula.text = currentItem.matricula
        holder.modelo.text = currentItem.modelo
        holder.anoFabrico.text = currentItem.ano_matricula


    }

    override fun getItemCount() = dataSource.size


    class VeiculoViewHolder(itemView: View, listener: OntItemClickListener?) :
        RecyclerView.ViewHolder(itemView) {
        val marca: TextView = itemView.maintextCar
        val modelo: TextView = itemView.tipoCar
        val matricula: TextView = itemView.matriculaCar
        val imageView: ImageView = itemView.imageCar
        val anoFabrico: TextView = itemView.anofabricoCar

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


