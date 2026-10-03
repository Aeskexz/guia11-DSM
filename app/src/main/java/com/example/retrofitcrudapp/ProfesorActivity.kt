package com.example.retrofitcrudapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

class ProfesorActivity : AppCompatActivity() {

    private lateinit var recyclerProfesores: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var fabAgregar: FloatingActionButton
    private lateinit var adapter: ProfesorAdapter

    private val addProfesorLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            cargarProfesores()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesor)

        recyclerProfesores = findViewById(R.id.recyclerProfesores)
        progressBar = findViewById(R.id.progressBar)
        fabAgregar = findViewById(R.id.fabAgregar)

        adapter = ProfesorAdapter(emptyList())
        recyclerProfesores.layoutManager = LinearLayoutManager(this)
        recyclerProfesores.adapter = adapter

        fabAgregar.setOnClickListener {
            addProfesorLauncher.launch(Intent(this, AgregarProfesorActivity::class.java))
        }

        cargarProfesores()
    }

    private fun cargarProfesores() {
        progressBar.visibility = View.VISIBLE

        ProfesorRetrofitClient.api.getProfesores().enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(call: Call<List<Profesor>>, response: Response<List<Profesor>>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    adapter.actualizarLista(lista)
                    Log.d("MockAPI", "Profesores recibidos: ${lista.size}")
                } else {
                    Toast.makeText(
                        this@ProfesorActivity,
                        "Error del servidor: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                progressBar.visibility = View.GONE
                Log.e("MockAPI", "Fallo de conexión", t)
                Toast.makeText(
                    this@ProfesorActivity,
                    "Error de conexión: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
