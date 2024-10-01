package com.bilalmirza.criticine.activities.movieDetail.fragments.moreLikeThis

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity.Companion.isSeries
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity.Companion.movieID
import com.bilalmirza.criticine.databinding.FragmentMoreLikeThisBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bilalmirza.criticine.utils.Constants
import com.bilalmirza.criticine.utils.Constants.MOVIE_ID_EXTRA

class MoreLikeThisFragment : Fragment(R.layout.fragment_more_like_this), MoreLikeThisView {
    private lateinit var binding: FragmentMoreLikeThisBinding
    private lateinit var adapter: MoreLikeThisAdapter
    private lateinit var presenter: MoreLikeThisPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoreLikeThisBinding.bind(view)

        //  setting up presenter
        presenter = MoreLikeThisPresenter(this)
        //  setting up recycler view
        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = MoreLikeThisAdapter(object : ItemClickListener<MovieShowResult> {
            override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                Intent(requireContext(), MovieDetailActivity::class.java).also {
                    it.putExtra(MOVIE_ID_EXTRA, item.id)
                    it.putExtra(Constants.IS_SERIES_EXTRAS, isSeries)
                    startActivity(it)
                }
            }
        })
        binding.searchResultRV.adapter = adapter
        binding.searchResultRV.layoutManager = GridLayoutManager(requireContext(), 3)

        if (isSeries) {
            presenter.getShowsLikeThis(movieID, requireContext())
        } else {
            presenter.getMoreLikeThis(movieID, requireContext())
        }
    }

    override fun onGetMoreFilmsLikeThis(similarMovieResponse: List<MovieShowResult>) {
        adapter.updateList(similarMovieResponse)
    }

    override fun onGetMoreShowsLikeThis(similarMovieResponse: List<MovieShowResult>) {
        adapter.updateList(similarMovieResponse)
    }
}