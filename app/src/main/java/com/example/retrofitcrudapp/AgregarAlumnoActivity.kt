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

class AgregarAlumnoActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etApellido: EditText
    private lateinit var etEdad: EditText
    private lateinit var btnGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_alumno)

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

            crearAlumno(Alumno(nombre = nombre, apellido = apellido, edad = edad))
        }
    }

    private fun crearAlumno(alumno: Alumno) {
        btnGuardar.isEnabled = false

        RetrofitClient.api.crearAlumno(alumno).enqueue(object : Callback<Alumno> {
            override fun onResponse(call: Call<Alumno>, response: Response<Alumno>) {
                btnGuardar.isEnabled = true
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@AgregarAlumnoActivity,
                        "Alumno agregado (ID: ${response.body()?.id})",
                        Toast.LENGTH_SHORT
                    ).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(
                        this@AgregarAlumnoActivity,
                        "No se pudo agregar: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Alumno>, t: Throwable) {
                btnGuardar.isEnabled = true
                Log.e("MockAPI", "Fallo al agregar alumno", t)
                Toast.makeText(
                    this@AgregarAlumnoActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
