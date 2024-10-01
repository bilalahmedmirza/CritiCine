package com.bilalmirza.criticine.activities.movieDetail.fragments.moreLikeThis

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.databinding.ItemSimilarBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bumptech.glide.Glide

class MoreLikeThisAdapter(private val clickListener: ItemClickListener<MovieShowResult>) :
    RecyclerView.Adapter<MoreLikeThisAdapter.MyViewHolder>() {
    private var similarFilmsList: List<MovieShowResult> = emptyList()

    class MyViewHolder(val binding: ItemSimilarBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            ItemSimilarBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = similarFilmsList[position]

        holder.itemView.apply {
            Glide.with(holder.itemView.context)
                .load("https://image.tmdb.org/t/p/w500${currentItem.posterPath}")
                .into(holder.binding.horizontalMovieScrollIV)
        }

        holder.binding.root.setOnClickListener {
            clickListener.onItemClick(currentItem, 0, 0)
        }
    }

    override fun getItemCount(): Int {
        return similarFilmsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(similarFilms: List<MovieShowResult>) {
        this.similarFilmsList = similarFilms
        notifyDataSetChanged()
    }
}