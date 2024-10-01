package com.bilalmirza.criticine.activities.mainActivity.fragments.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity
import com.bilalmirza.criticine.databinding.FragmentBookmarksBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bilalmirza.criticine.utils.Constants
import com.bilalmirza.criticine.utils.Constants.MOVIE_ID_EXTRA

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks), BookmarksFragmentView {
    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var adapter: BookmarksFragmentAdapter
    private lateinit var presenter: BookmarksFragmentPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookmarksBinding.bind(view)

        //  initialising presenter
        presenter = BookmarksFragmentPresenter(this)
        //  setting up recycler view
        setUpRV()
    }

    private fun setUpRV() {
        adapter = BookmarksFragmentAdapter(object : ItemClickListener<MovieShowResult> {
            override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                if (type == 1) {
                    presenter.deleteFilmShow(item.uUID)
                } else {
                    Intent(requireContext(), MovieDetailActivity::class.java).also {
                        it.putExtra(MOVIE_ID_EXTRA, item.id)
                        it.putExtra(Constants.IS_SERIES_EXTRAS, item.isSeries)
                        startActivity(it)
                    }
                }
            }
        })
        binding.savedRV.adapter = adapter
        binding.savedRV.layoutManager = LinearLayoutManager(requireContext())

        presenter.getSavedShowMovies()
    }

    override fun onSuccessGet(movieShowResultList: List<MovieShowResult>) {
        adapter.updateList(movieShowResultList)
    }
}