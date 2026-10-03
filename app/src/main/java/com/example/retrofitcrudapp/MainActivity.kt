package com.example.retrofitcrudapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerAlumnos: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var fabAgregar: FloatingActionButton
    private lateinit var btnAlumnos: Button
    private lateinit var btnProfesores: Button
    private lateinit var adapter: AlumnoAdapter

    private val addAlumnoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            cargarAlumnos()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerAlumnos = findViewById(R.id.recyclerAlumnos)
        progressBar = findViewById(R.id.progressBar)
        fabAgregar = findViewById(R.id.fabAgregar)
        btnAlumnos = findViewById(R.id.btnAlumnos)
        btnProfesores = findViewById(R.id.btnProfesores)

        adapter = AlumnoAdapter(emptyList())
        recyclerAlumnos.layoutManager = LinearLayoutManager(this)
        recyclerAlumnos.adapter = adapter

        btnAlumnos.setOnClickListener {
            btnAlumnos.setBackgroundColor(getColor(android.R.color.holo_purple))
            btnProfesores.setBackgroundColor(getColor(android.R.color.darker_gray))
            cargarAlumnos()
        }

        btnProfesores.setOnClickListener {
            startActivity(Intent(this, ProfesorActivity::class.java))
        }

        fabAgregar.setOnClickListener {
            addAlumnoLauncher.launch(Intent(this, AgregarAlumnoActivity::class.java))
        }

        cargarAlumnos()
    }

    private fun cargarAlumnos() {
        progressBar.visibility = View.VISIBLE

        RetrofitClient.api.getAlumnos().enqueue(object : Callback<List<Alumno>> {
            override fun onResponse(call: Call<List<Alumno>>, response: Response<List<Alumno>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    adapter.actualizarLista(lista)
                    Log.d("MockAPI", "Alumnos recibidos: ${lista.size}")
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Error del servidor: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Alumno>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("MockAPI", "Fallo de conexión", t)
                Toast.makeText(
                    this@MainActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}