package com.example.retrofitcrudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfesorAdapter(private var profesores: List<Profesor>) :
    RecyclerView.Adapter<ProfesorAdapter.ProfesorViewHolder>() {

    class ProfesorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatar)
        val tvNombreCompleto: TextView = itemView.findViewById(R.id.tvNombreCompleto)
        val tvEdad: TextView = itemView.findViewById(R.id.tvEdad)
        val tvId: TextView = itemView.findViewById(R.id.tvId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alumno, parent, false)
        return ProfesorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfesorViewHolder, position: Int) {
        val profesor = profesores[position]
        holder.ivAvatar.setImageResource(R.drawable.avatar)
        holder.tvNombreCompleto.text = "${profesor.nombre.trim()} ${profesor.apellido.trim()}"
        holder.tvEdad.text = "${profesor.edad}"
        holder.tvId.text = "${profesor.id}"
    }

    override fun getItemCount(): Int = profesores.size

    fun actualizarLista(nuevaLista: List<Profesor>) {
        profesores = nuevaLista
        notifyDataSetChanged()
    }
}
