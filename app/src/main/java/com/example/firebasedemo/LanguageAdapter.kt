package com.example.firebasedemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class LanguageAdapter(var languages: List<String>, private val context: Context) :
    RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtLanguageName: TextView = itemView.findViewById(R.id.txtLanguageName)
        fun bindData(pos: Int) {
            txtLanguageName.text = languages[pos]
            itemView.setOnClickListener {
                Toast.makeText(context, languages[pos], Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_language, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = languages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }
}