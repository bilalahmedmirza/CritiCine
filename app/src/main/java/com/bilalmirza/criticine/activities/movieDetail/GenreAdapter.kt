package com.bilalmirza.criticine.activities.movieDetail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.databinding.ItemGenreBinding
import com.bilalmirza.criticine.model.genre.Genre

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.MyViewHolder>() {
    private var genreList: List<Genre> = emptyList()

    class MyViewHolder(val binding: ItemGenreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = genreList[position]

        holder.binding.genreName.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Genre>) {
        genreList = newList
        notifyDataSetChanged()
    }
}