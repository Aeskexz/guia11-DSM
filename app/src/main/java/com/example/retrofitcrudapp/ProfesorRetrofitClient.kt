package com.example.retrofitcrudapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProfesorRetrofitClient {
    private const val BASE_URL = "https://6ac161b3309c92da039c62cc.mockapi.io/api/escuela/"

    val api: ProfesorApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfesorApi::class.java)
    }
}
