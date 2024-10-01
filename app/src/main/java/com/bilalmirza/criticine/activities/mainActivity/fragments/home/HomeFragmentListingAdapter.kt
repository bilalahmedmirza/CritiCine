package com.bilalmirza.criticine.activities.mainActivity.fragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.databinding.ItemHomeScreenBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bumptech.glide.Glide

class HomeFragmentListingAdapter(private val clickListener: ItemClickListener<MovieShowResult>) :
    RecyclerView.Adapter<HomeFragmentListingAdapter.MyViewHolder>() {
    private var movieShowResultList: List<MovieShowResult> = emptyList()

    class MyViewHolder(val binding: ItemHomeScreenBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            ItemHomeScreenBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = movieShowResultList[position]
        val urlPosterImage = "https://image.tmdb.org/t/p/w500${currentItem.posterPath}"

        holder.itemView.apply {
            Glide.with(holder.itemView.context).load(urlPosterImage)
                .into(holder.binding.horizontalMovieScrollIV)
        }

        holder.binding.root.setOnClickListener {
            clickListener.onItemClick(currentItem, 0, 0)
        }
    }

    override fun getItemCount(): Int {
        return movieShowResultList.take(10).size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<MovieShowResult>) {
        this.movieShowResultList = list
        notifyDataSetChanged()
    }
}