package com.example.stepik.favorites

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stepik.R

class FavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val img: ImageView = itemView.findViewById(R.id.iv_favs)
    private val name: TextView = itemView.findViewById(R.id.tv_favs_name)

    fun bind(fav: Favorite) {
        img.setImageResource(fav.image)
        name.text = fav.name
    }
}
