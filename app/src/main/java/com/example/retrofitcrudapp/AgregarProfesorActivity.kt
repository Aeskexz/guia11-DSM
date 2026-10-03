package com.example.retrofitcrudapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgregarProfesorActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etEdad: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_profesor)

        etNombre = findViewById(R.id.etNombre)
        etApellido = findViewById(R.id.etApellido)
        etEdad = findViewById(R.id.etEdad)
        btnGuardar = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val edad = etEdad.text.toString().trim().toIntOrNull()

            if (nombre.isEmpty() || apellido.isEmpty() || edad == null) {
                Toast.makeText(this, "Complete todos los campos correctamente", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            crearProfesor(Profesor(nombre = nombre, apellido = apellido, edad = edad))
        }
    }

    private fun crearProfesor(profesor: Profesor) {
        btnGuardar.isEnabled = false

        ProfesorRetrofitClient.api.crearProfesor(profesor).enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                btnGuardar.isEnabled = true
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@AgregarProfesorActivity,
                        "Profesor agregado (ID: ${response.body()?.id})",
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(
                        this@AgregarProfesorActivity,
                        "No se pudo agregar: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                btnGuardar.isEnabled = true
                Log.e("MockAPI", "Fallo al agregar profesor", t)
                Toast.makeText(
                    this@AgregarProfesorActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
