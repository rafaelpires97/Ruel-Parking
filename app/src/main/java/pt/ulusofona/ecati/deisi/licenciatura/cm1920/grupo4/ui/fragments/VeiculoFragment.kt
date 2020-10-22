package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.fragment_veiculo.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.AppDataBase
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.activities.listVeiculo
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.adapter.VeiculoAdapter

class VeiculoFragment : Fragment() {

    lateinit var mAdapter: VeiculoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_veiculo, container, false)
        return view
    }

    override fun onStart() {
        buildRecyclerView()


        button_insert.setOnClickListener {
            activity?.supportFragmentManager?.let { NavigationManager.goToInsertVeiculoFragment(it) }
        }

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

                if (direction == ItemTouchHelper.LEFT) {
                    AppDataBase.getDatabase(requireContext()).veiculoDAO()
                        .delete(listVeiculo[viewHolder.adapterPosition])
                    Toast.makeText(activity, R.string.car_deleted, Toast.LENGTH_SHORT).show()
                    mAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                    buildRecyclerView()
                } else {
                    if (direction == ItemTouchHelper.RIGHT) {
                        context?.let {
                            NavigationManager.goToSendSMS(
                                listVeiculo[viewHolder.adapterPosition].matricula,
                                it
                            )
                        }
                    }
                }

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

                RecyclerViewSwipeDecorator.Builder(
                    activity as Context,
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(activity as Context,R.color.redDelete))
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(activity as Context,R.color.colorAccent))
                    .addSwipeRightActionIcon(R.drawable.reboque)
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


        }).attachToRecyclerView(recycler_viewCar)

        super.onStart()
    }

    override fun onResume() {
        buildRecyclerView()
        super.onResume()
    }


    fun buildRecyclerView() {
        listVeiculo = AppDataBase.getDatabase(activity as Context).veiculoDAO().getList()
        mAdapter =
            VeiculoAdapter(
                listVeiculo
            )
        recycler_viewCar.adapter = mAdapter
        recycler_viewCar.layoutManager = LinearLayoutManager(activity)
        recycler_viewCar.setHasFixedSize(true)

        mAdapter.setOnItemClickListener(object : VeiculoAdapter.OntItemClickListener {
            override fun onItemClick(position: Int) {
                newVeiculo = listVeiculo[position]

                activity?.supportFragmentManager?.let { NavigationManager.goToUpdateFragment(it) }
            }
        })

    }
}