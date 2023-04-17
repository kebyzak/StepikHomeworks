package com.example.stepik.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stepik.R

class FavoritesAdapter(private val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<FavoritesViewHolder>() {
    private val favs: MutableList<Favorite> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoritesViewHolder(layoutInflater.inflate(R.layout.item_player, parent, false))

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) =
        holder.bind(favs[position])

    override fun getItemCount() = favs.size

    fun setData(newData: List<Favorite>) {
        favs.clear()
        favs.addAll(newData)
        notifyDataSetChanged()
    }
}