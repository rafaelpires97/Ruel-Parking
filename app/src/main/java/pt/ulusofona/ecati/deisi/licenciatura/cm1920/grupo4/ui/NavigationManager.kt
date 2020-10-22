package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragment.ContactosFragment
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragment.DefinitionFragment
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragment.InfoParqueFragment
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragments.*

class NavigationManager {
    companion object {
        private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
            val transition = fm.beginTransaction()
            transition.replace(R.id.fragment_container, fragment)
            transition.addToBackStack(null)
            transition.commit()
        }

        fun goToHomeFragment(fm: FragmentManager) {
            placeFragment(
                fm,
                HomeFragment()
            )

        }

        fun goToHistoryFragment(fm: FragmentManager) {
            placeFragment(
                fm,
                HistoryFragment()
            )
        }


        fun goToContactos(fm: FragmentManager) {
            placeFragment(
                fm,
                ContactosFragment()
            )
        }

        fun goToMapFragment(fm: FragmentManager) {
            placeFragment(
                fm,
                MapFragment()
            )
        }

        fun goToGiras(fm: FragmentManager) {
            placeFragment(
                fm,
                GiraStationsFragment()
            )
        }



        fun goToDefinitionFragment(fm: FragmentManager) {
            placeFragment(
                fm,
                DefinitionFragment()
            )
        }

        fun goToVeiculoFragment(fm: FragmentManager) {
            placeFragment(
                fm,
                VeiculoFragment()
            )
        }

        fun goToInsertVeiculoFragment(fm: FragmentManager) {
            placeFragment(
                fm,
                InsertVeiculoFragment()
            )
        }


        fun goToHistoricoAddFragment(fm: FragmentManager) {
            placeFragment(
                fm,
                HistoricoAddFragment()
            )
        }

        fun goToParkMeNowFragment(fm: FragmentManager) {
            placeFragment(
                fm,
                ParkMeNowFragment()
            )
        }

        fun goToUpdateFragment(fm:FragmentManager){
            placeFragment(fm,UpdateFragment())
        }

        fun goToInfoVeiculoFragment(fm:FragmentManager){
            placeFragment(fm,InfoParqueFragment())
        }

        fun goToFilterFragment(fm:FragmentManager){
            //placeFragment(fm,FragmentFilter2())
        }


        fun goToSendSMS(matricula: String, context: Context){

            var uri : Uri = Uri.parse("smsto:3838")
            val intent = Intent(Intent.ACTION_SENDTO, uri)

            intent.putExtra("sms_body", "Reboque "+ matricula)
            context.startActivity(intent)

        }
    }

}