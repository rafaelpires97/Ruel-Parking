package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.remote.service

import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.data.entities.Parque
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ParqueService {


    @GET("opendata/parking/lots")
    fun getParkingLots(@Header("api_key") token: String): Call<List<Parque>>


}