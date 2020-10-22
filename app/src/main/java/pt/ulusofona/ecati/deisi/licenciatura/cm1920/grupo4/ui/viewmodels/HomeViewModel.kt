package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.domain.HomeLogic
import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.ParqueRepository

class HomeViewModel ( application: Application) : AndroidViewModel(application) {


    private val homeLogic = HomeLogic(repository = ParqueRepository(getApplication()))

    fun registerListener(){
        homeLogic.registerListener()
    }

}