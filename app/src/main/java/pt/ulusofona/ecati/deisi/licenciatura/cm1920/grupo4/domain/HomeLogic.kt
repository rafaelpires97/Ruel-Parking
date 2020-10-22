package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.domain

import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.local.parque.ParqueRepository

class HomeLogic(private val repository: ParqueRepository) {


    fun registerListener(){
        repository.registerListener()

    }
}