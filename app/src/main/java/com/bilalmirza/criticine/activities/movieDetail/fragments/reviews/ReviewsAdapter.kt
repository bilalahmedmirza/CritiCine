package com.bilalmirza.criticine.activities.movieDetail.fragments.reviews

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.databinding.ItemReviewBinding
import com.bilalmirza.criticine.interfaces.PaginationListener
import com.bilalmirza.criticine.model.reviews.ReviewsResult
import com.bumptech.glide.Glide

class ReviewsAdapter(
    private val paginationListener: PaginationListener
) : RecyclerView.Adapter<ReviewsAdapter.MyViewHolder>() {
    private var filmReviews: ArrayList<ReviewsResult> = arrayListOf()

    class MyViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = filmReviews[position]

        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${currentItem.authorDetails.avatarPath}")
            .placeholder(
                R.drawable.dummy_profile_photo
            ).into(holder.binding.userProfilePicture)
        holder.binding.userName.text = currentItem.authorDetails.username
        holder.binding.reviewTV.text = currentItem.content

        if (position == filmReviews.lastIndex - 1) {
            paginationListener.onNextPage()
        }

        holder.binding.sentimentTextTV.text = analyseScoreText(currentItem.sentimentScore!!)
        holder.binding.sentimentTextTV.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context, analyseScoreColor(currentItem.sentimentScore!!)
            )
        )

        holder.binding.sentimentScoreTV.text = currentItem.sentimentScore!!.toString()

        holder.binding.sentimentTextTV.isVisible = true
        holder.binding.sentimentScoreTV.isVisible = true
    }

    private fun analyseScoreText(score: Float): String {
        return when (score) {
            in 0.25..1.00 -> {
                "Excellent Review"
            }

            in -0.25..0.25 -> {
                "Mixed Review"
            }

            in -1.0..-0.25 -> {
                "Bad Review"
            }

            else -> ""
        }
    }

    private fun analyseScoreColor(score: Float): Int {
        return when (score) {
            in 0.25..1.00 -> {
                R.color.green
            }

            in -0.25..0.25 -> {
                R.color.gold
            }

            in -1.0..-0.25 -> {
                R.color.primary_color
            }

            else -> R.color.white
        }
    }

    override fun getItemCount(): Int {
        return filmReviews.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<ReviewsResult>) {
        this.filmReviews = ArrayList(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: List<ReviewsResult>) {
        this.filmReviews.addAll(list)
        notifyDataSetChanged()
    }
}