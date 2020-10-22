package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.remote.service

import pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo3.data.remote.responses.GiraStation.GiraStation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header


interface BykeService {


    @GET("opendata/gira/station/list")
    fun getBike(@Header ("api_key") token: String): Call<GiraStation>


}