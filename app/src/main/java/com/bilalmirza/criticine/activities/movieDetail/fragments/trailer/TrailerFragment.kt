package com.bilalmirza.criticine.activities.movieDetail.fragments.trailer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity.Companion.isSeries
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity.Companion.movieID
import com.bilalmirza.criticine.databinding.FragmentTrailerBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.model.trailer.TrailerResult

class TrailerFragment : Fragment(R.layout.fragment_trailer), TrailerFragmentView {
    private lateinit var binding: FragmentTrailerBinding
    private lateinit var myPresenter: TrailerFragmentPresenter
    private lateinit var myAdapter: TrailerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTrailerBinding.bind(view)

        //  setting up trailer presenter
        myPresenter = TrailerFragmentPresenter(this)
        //  setting up click listeners
        clickListeners()
        //  getting trailer of film
        gettingTrailer(requireContext())
        //  setting up trailer recycler view
        setRecyclerView()
    }

    private fun setRecyclerView() {
        myAdapter = TrailerAdapter(object : ItemClickListener<TrailerResult> {
            override fun onItemClick(item: TrailerResult, pos: Int, type: Int) {
                val intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=${item.key}")
                )
                startActivity(intent)
            }
        })
        binding.trailerRecyclerView.adapter = myAdapter
        binding.trailerRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.trailerRecyclerView)
    }

    private fun gettingTrailer(context: Context) {
        if (isSeries) {
            myPresenter.getSeriesTrailer(movieID, context)
        } else {
            myPresenter.getFilmTrailer(movieID, context)
        }
    }

    override fun onSeriesTrailerSuccess(trailerResult: List<TrailerResult>) {
        myAdapter.updateList(trailerResult)
    }

    private fun clickListeners() {}

    override fun onTrailerSuccess(trailerResult: List<TrailerResult>) {
        myAdapter.updateList(trailerResult)
    }
}