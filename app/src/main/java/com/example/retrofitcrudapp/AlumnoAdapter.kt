package com.example.retrofitcrudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlumnoAdapter(private var alumnos: List<Alumno>) :
    RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder>() {

    class AlumnoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvNombreCompleto: TextView = itemView.findViewById(R.id.tvNombreCompleto)
        val tvEdad: TextView = itemView.findViewById(R.id.tvEdad)
        val tvId: TextView = itemView.findViewById(R.id.tvId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlumnoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alumno, parent, false)
        return AlumnoViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlumnoViewHolder, position: Int) {
        val alumno = alumnos[position]
        holder.ivAvatar.setImageResource(R.drawable.avatar)
        holder.tvNombreCompleto.text = "${alumno.nombre.trim()} ${alumno.apellido.trim()}"
        holder.tvEdad.text = "${alumno.edad}"
        holder.tvId.text = "${alumno.id}"
    }

    override fun getItemCount(): Int = alumnos.size

    fun actualizarLista(nuevaLista: List<Alumno>) {
        alumnos = nuevaLista
        notifyDataSetChanged()
    }
}
