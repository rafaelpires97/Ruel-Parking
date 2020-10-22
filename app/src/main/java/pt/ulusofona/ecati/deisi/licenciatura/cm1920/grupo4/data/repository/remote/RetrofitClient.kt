package pt.ulusofona.ecati.deisi.licenciatura.cm1920.grupo4.service.repository.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient private constructor() {

    companion object {
        private lateinit var retrofit: Retrofit
        private val baseurl = "https://emel.city-platform.com/"


        fun getRetrofitInstance(): Retrofit {
            val httpClient = OkHttpClient.Builder()
            if (!Companion::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseurl)
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }


        fun <S> createService(serviceClass: Class<S>): S {
            return getRetrofitInstance().create(serviceClass)
        }


    }
}