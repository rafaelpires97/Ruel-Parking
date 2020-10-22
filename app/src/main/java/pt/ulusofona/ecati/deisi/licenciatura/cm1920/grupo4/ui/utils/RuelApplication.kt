package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.ui.utils

import FusedLocation
import android.app.Application

class RuelApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        FusedLocation.start(this)
    }







}