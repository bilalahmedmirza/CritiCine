package com.bilalmirza.criticine.activities.movieDetail.fragments.trailer

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.databinding.ItemTrailerBinding
import com.bilalmirza.criticine.model.trailer.TrailerResult
import com.bumptech.glide.Glide

class TrailerAdapter(private val clickListener: ItemClickListener<TrailerResult>) :
    RecyclerView.Adapter<TrailerAdapter.MyViewHolder>() {
    private var trailerList: List<TrailerResult> = emptyList()

    class MyViewHolder(val binding: ItemTrailerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = trailerList[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load("https://i.ytimg.com/vi/${currentItem.key}/hqdefault.jpg")
                .into(holder.binding.roundedImageView)
        }

        holder.binding.youtubeButton.setOnClickListener {
            clickListener.onItemClick(currentItem, 0, 0)
        }
    }

    override fun getItemCount(): Int {
        return trailerList.take(5).size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<TrailerResult>) {
        this.trailerList = list
        notifyDataSetChanged()
    }
}