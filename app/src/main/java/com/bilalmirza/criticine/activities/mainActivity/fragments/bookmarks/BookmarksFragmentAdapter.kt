package com.bilalmirza.criticine.activities.mainActivity.fragments.bookmarks

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.databinding.ItemSavedBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bumptech.glide.Glide

class BookmarksFragmentAdapter(private val clickListener: ItemClickListener<MovieShowResult>) :
    RecyclerView.Adapter<BookmarksFragmentAdapter.MyViewHolder>() {
    private var savedShowMovieList: ArrayList<MovieShowResult> = arrayListOf()

    class MyViewHolder(val binding: ItemSavedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSavedBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return savedShowMovieList.size
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = savedShowMovieList[position]

        val doubleRating = String.format("%.1f", currentItem.voteAverage)
        holder.binding.filmRating.text = doubleRating

        holder.binding.filmOverview.text = currentItem.overview

        holder.binding.titleFilm.text = currentItem.title ?: currentItem.name

        Glide.with(holder.itemView).load("https://image.tmdb.org/t/p/w500${currentItem.posterPath}")
            .into(holder.binding.filmPosterIV)

        holder.binding.titleFilm.isSelected = true

        holder.binding.root.setOnClickListener {
            clickListener.onItemClick(currentItem, 0, 0)
        }

        holder.binding.deleteBtn.setOnClickListener {
            clickListener.onItemClick(currentItem, 0, 1)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MovieShowResult>) {
        this.savedShowMovieList = ArrayList(list)
        notifyDataSetChanged()
    }
}