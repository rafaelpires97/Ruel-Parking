package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_definition.*
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.R
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.NavigationManager


class DefinitionFragment : Fragment(){

    var darkModeOn : Boolean = false
    var darkmodeContexto : Boolean = false
    var shake : Boolean = false

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_definition,container,false)
        darkModeOn = context?.getSharedPreferences("ruel", 0)!!.getBoolean("darkmode", false)
        darkmodeContexto =  context?.getSharedPreferences("ruel", 0)!!.getBoolean("darkmodeContexto", true)
        shake = context?.getSharedPreferences("ruel", 0)!!.getBoolean("shake", true)
        return view

    }


    override fun onStart() {
        super.onStart()
        darkmodeswitch.isChecked = darkModeOn
        darkmodeContextoSwitch.isChecked= darkmodeContexto
        shakeSwitch.isChecked = shake
        contactos.setOnClickListener {
            activity?.supportFragmentManager?.let { NavigationManager.goToContactos(it) }
        }
        setDarkMode()
        setShake()
        setDarkModeContext()
    }


    fun setDarkMode(){
        darkmodeswitch.setOnCheckedChangeListener{ button,isChecked ->

            darkModeOn = !darkModeOn
            Log.i("darkMode", ""+ darkModeOn)
            if(darkModeOn){
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("darkmode", true).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("darkmode", false).apply()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    fun setDarkModeContext(){
        darkmodeContextoSwitch.setOnCheckedChangeListener{ button,isChecked ->

            darkmodeContexto = !darkmodeContexto
            Log.i("darkMode", ""+ darkModeOn)
            if(darkmodeContexto){
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("darkmodeContexto", true).apply()
            } else{
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("darkmodeContexto", false).apply()
                if(!darkModeOn){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    fun setShake(){
        shakeSwitch.setOnCheckedChangeListener { button, isChecked ->
            shake = !shake
            if(shake){
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("shake", true).apply()
            }else{
                context?.getSharedPreferences("ruel", 0)!!.edit().putBoolean("shake", false).apply()
            }
        }
    }

}