package com.bilalmirza.criticine.activities.listMovies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.databinding.ItemSearchBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.interfaces.PaginationListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bumptech.glide.Glide

class ListMoviesAdapter(
    private val clickListener: ItemClickListener<MovieShowResult>,
    private val paginationListener: PaginationListener
) : RecyclerView.Adapter<ListMoviesAdapter.MyViewHolder>() {
    private var movieShowResultList: ArrayList<MovieShowResult> = arrayListOf()

    class MyViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = movieShowResultList[position]
        val filmPosterURL = "https://image.tmdb.org/t/p/w500${currentItem.posterPath}"
        val doubleRating = String.format("%.1f", currentItem.voteAverage)

        holder.itemView.apply {
            Glide.with(this).load(filmPosterURL).into(holder.binding.filmPosterIV)
        }
        holder.binding.titleFilm.text = currentItem.title ?: currentItem.name
        holder.binding.filmRating.text = doubleRating
        holder.binding.filmOverview.text = currentItem.overview

        holder.binding.titleFilm.isSelected = true

        if (position == movieShowResultList.lastIndex - 1) {
            paginationListener.onNextPage()
        }
        holder.binding.root.setOnClickListener {
            clickListener.onItemClick(currentItem, 0, 0)
        }
    }

    override fun getItemCount(): Int {
        return movieShowResultList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MovieShowResult>) {
        this.movieShowResultList = ArrayList(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<MovieShowResult>) {
        this.movieShowResultList.addAll(list)
        notifyDataSetChanged()
    }
}