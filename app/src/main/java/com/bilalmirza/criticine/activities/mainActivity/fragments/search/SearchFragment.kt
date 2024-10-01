package com.bilalmirza.criticine.activities.mainActivity.fragments.search

import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity
import com.bilalmirza.criticine.databinding.FragmentSearchBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.interfaces.PaginationListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bilalmirza.criticine.utils.Constants
import com.bilalmirza.criticine.utils.Constants.MOVIE_ID_EXTRA

class SearchFragment : Fragment(R.layout.fragment_search), SearchFragmentView {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var moviesAdapter: SearchFragmentAdapter
    private lateinit var seriesAdapter: SearchFragmentAdapter
    private lateinit var presenter: SearchFragmentPresenter
    private var currentPage = 1
    private var lastPage = 1
    private var currentQuery = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        //  setting up presenter
        presenter = SearchFragmentPresenter(this)
        //  setting up recycler views
        initSeriesAdapter()
        initMoviesAdapter()
        //  search for movies and shows
        clickListeners()
        //  by default movie button is selected and default adapter is movie adapter
        binding.movieSearchBtn.isSelected = true
    }

    private fun initSeriesAdapter() {
        seriesAdapter = SearchFragmentAdapter(object : ItemClickListener<MovieShowResult> {
            override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                Intent(requireContext(), MovieDetailActivity::class.java).apply {
                    putExtra(MOVIE_ID_EXTRA, item.id)
                    putExtra(Constants.IS_SERIES_EXTRAS, true)
                    startActivity(this)
                }
            }
        }, object : PaginationListener {
            override fun onNextPage() {
                if (currentPage < lastPage) {
                    presenter.searchTitleByName(
                        currentQuery,
                        requireContext(),
                        binding.movieSearchBtn.isSelected,
                        binding.seriesSearchBtn.isSelected,
                        currentPage + 1
                    )
                }
            }
        })
    }

    private fun clickListeners() {
        binding.searchTitleET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val view: View? = this.view
                if (view != null) {
                    currentQuery = binding.searchTitleET.text.toString()
                    presenter.searchTitleByName(
                        currentQuery,
                        requireContext(),
                        binding.movieSearchBtn.isSelected,
                        binding.seriesSearchBtn.isSelected,
                        1
                    )

                    val inputMethodManager =
                        context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
                binding.searchTitleET.clearFocus()
            }
            true
        }
        binding.movieSearchBtn.setOnClickListener {
            binding.movieSearchBtn.isSelected = true
            binding.seriesSearchBtn.isSelected = false
            binding.searchResultRV.adapter = moviesAdapter

            presenter.searchTitleByName(
                binding.searchTitleET.text.toString(),
                requireContext(),
                binding.movieSearchBtn.isSelected,
                binding.seriesSearchBtn.isSelected,
                1
            )
        }
        binding.seriesSearchBtn.setOnClickListener {
            binding.movieSearchBtn.isSelected = false
            binding.seriesSearchBtn.isSelected = true
            binding.searchResultRV.adapter = seriesAdapter

            presenter.searchTitleByName(
                binding.searchTitleET.text.toString(),
                requireContext(),
                binding.movieSearchBtn.isSelected,
                binding.seriesSearchBtn.isSelected,
                1
            )
        }
    }

    override fun onError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun loadProgressBar(isForPagination: Boolean) {
        if (!isForPagination) {
            binding.loader.visibility = View.VISIBLE
        }
    }

    override fun hideLoader() {
        binding.loader.visibility = View.GONE
    }

    private fun initMoviesAdapter() {
        moviesAdapter = SearchFragmentAdapter(object : ItemClickListener<MovieShowResult> {
            override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                Intent(requireContext(), MovieDetailActivity::class.java).apply {
                    putExtra(MOVIE_ID_EXTRA, item.id)
                    startActivity(this)
                }
            }
        }, object : PaginationListener {
            override fun onNextPage() {
                if (currentPage < lastPage) {
                    presenter.searchTitleByName(
                        currentQuery,
                        requireContext(),
                        binding.movieSearchBtn.isSelected,
                        binding.seriesSearchBtn.isSelected,
                        currentPage + 1
                    )
                }
            }
        })
        binding.searchResultRV.adapter = moviesAdapter
        binding.searchResultRV.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onSearchShow(
        searchResult: List<MovieShowResult>, currentPage: Int, lastPage: Int
    ) {
        this.currentPage = currentPage
        this.lastPage = lastPage

        if (currentPage > 1) {
            seriesAdapter.addList(searchResult)
        } else {
            seriesAdapter.updateList(searchResult)
        }
    }

    override fun onSearchFilm(
        searchResult: List<MovieShowResult>, currentPage: Int, lastPage: Int
    ) {
        this.currentPage = currentPage
        this.lastPage = lastPage

        if (currentPage > 1) {
            moviesAdapter.addList(searchResult)
        } else {
            moviesAdapter.updateList(searchResult)
        }
    }
}