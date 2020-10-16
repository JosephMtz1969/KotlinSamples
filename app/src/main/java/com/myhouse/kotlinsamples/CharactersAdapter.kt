package com.myhouse.kotlinsamples

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myhouse.kotlinsamples.network.CharacterResult

class CharactersAdapter(private val characters: List<CharacterResult>, private val context: Context) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(LayoutInflater.from(context).inflate(R.layout.character_item, parent, false))
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.nameTextView.text = characters[position].name
        holder.speciesTextView.text = characters[position].species
        holder.statusTextView.text = characters[position].status
    }

    override fun getItemCount(): Int {
        return characters.size
    }

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.name)
        val speciesTextView: TextView = view.findViewById(R.id.species)
        val statusTextView: TextView = view.findViewById(R.id.status)

    }
}