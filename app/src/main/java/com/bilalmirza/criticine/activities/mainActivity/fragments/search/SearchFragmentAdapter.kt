package com.bilalmirza.criticine.activities.mainActivity.fragments.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.databinding.ItemSearchBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.interfaces.PaginationListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bumptech.glide.Glide

class SearchFragmentAdapter(
    private val clickListener: ItemClickListener<MovieShowResult>,
    private val paginationListener: PaginationListener
) : RecyclerView.Adapter<SearchFragmentAdapter.MyViewHolder>() {
    private var searchResultList: ArrayList<MovieShowResult> = arrayListOf()

    class MyViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = searchResultList[position]

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${currentItem.posterPath}")
            .into(holder.binding.filmPosterIV)

        holder.binding.titleFilm.text = currentItem.title ?: currentItem.name

        val doubleRating = String.format("%.1f", currentItem.voteAverage)
        holder.binding.filmRating.text = doubleRating

        holder.binding.filmOverview.text = currentItem.overview

        holder.binding.titleFilm.isSelected = true

        if (position == searchResultList.lastIndex - 1) {
            paginationListener.onNextPage()
        }
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(currentItem, 0, 0)
        }
    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MovieShowResult>) {
        this.searchResultList = ArrayList(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<MovieShowResult>) {
        this.searchResultList.addAll(list)
        notifyDataSetChanged()
    }
}