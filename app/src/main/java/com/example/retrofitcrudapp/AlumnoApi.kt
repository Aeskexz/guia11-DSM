package com.example.retrofitcrudapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AlumnoApi {
    @GET("alumno")
    fun getAlumnos(): Call<List<Alumno>>

    @POST("alumno")
    fun crearAlumno(@Body alumno: Alumno): Call<Alumno>
}
