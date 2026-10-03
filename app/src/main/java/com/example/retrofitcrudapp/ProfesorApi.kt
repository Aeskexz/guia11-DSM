package com.example.retrofitcrudapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProfesorApi {
    @GET("profesor")
    fun getProfesores(): Call<List<Profesor>>

    @POST("profesor")
    fun crearProfesor(@Body profesor: Profesor): Call<Profesor>
}
