package com.example.retrofitcrudapp

import com.google.gson.annotations.SerializedName

data class Alumno(
    @SerializedName("id") val id: String? = null,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("edad") val edad: Int
)
