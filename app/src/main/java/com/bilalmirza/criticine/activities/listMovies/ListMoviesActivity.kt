package com.bilalmirza.criticine.activities.listMovies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bilalmirza.criticine.activities.listMovies.adapter.ListMoviesAdapter
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity
import com.bilalmirza.criticine.databinding.ActivityListMoviesBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.interfaces.PaginationListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bilalmirza.criticine.utils.Constants
import com.bilalmirza.criticine.utils.Constants.MOVIE_ID_EXTRA

class ListMoviesActivity : AppCompatActivity(), ListMoviesView {
    private lateinit var binding: ActivityListMoviesBinding
    private lateinit var myPresenter: ListMoviesPresenter
    private lateinit var popularAdapter: ListMoviesAdapter
    private lateinit var playNowAdapter: ListMoviesAdapter
    private lateinit var bestSeriesAdapter: ListMoviesAdapter
    private var currentPage = 1
    private var lastPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  setting up presenter
        myPresenter = ListMoviesPresenter(this)
        //  setting title and calling api on that basis
        receiveData()
    }

    @SuppressLint("SetTextI18n")
    private fun receiveData() {
        val headingType = intent.getIntExtra(Constants.MOVIE_TYPE_EXTRAS, 99)
        when (headingType) {
            Constants.TYPE_POPULAR -> {
                binding.titleTV.text = "Most Popular"
                callMostPopular()
            }

            Constants.TYPE_PLAYING_NOW -> {
                binding.titleTV.text = "Playing Now"
                callPlayingNow()
            }

            Constants.TYPE_BEST_SERIES -> {
                binding.titleTV.text = "Best Series"
                callBestSeries()
            }
        }
    }

    private fun callBestSeries() {
        bestSeriesAdapter = ListMoviesAdapter(object : ItemClickListener<MovieShowResult> {
            override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                Intent(this@ListMoviesActivity, MovieDetailActivity::class.java).also {
                    it.putExtra(MOVIE_ID_EXTRA, item.id)
                    it.putExtra(Constants.IS_SERIES_EXTRAS, true)
                    startActivity(it)
                }
            }
        }, object : PaginationListener {
            override fun onNextPage() {
                if (currentPage < lastPage) {
                    myPresenter.getBestSeries(
                        this@ListMoviesActivity, currentPage + 1
                    )
                }
            }
        })
        binding.listMoviesRV.adapter = bestSeriesAdapter
        binding.listMoviesRV.layoutManager = LinearLayoutManager(this)

        myPresenter.getBestSeries(this, currentPage)
    }

    private fun callPlayingNow() {
        playNowAdapter = ListMoviesAdapter(object : ItemClickListener<MovieShowResult> {
            override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                Intent(this@ListMoviesActivity, MovieDetailActivity::class.java).also {
                    it.putExtra(MOVIE_ID_EXTRA, item.id)
                    startActivity(it)
                }
            }
        }, object : PaginationListener {
            override fun onNextPage() {
                if (currentPage < lastPage) {
                    myPresenter.getPlayingNowFilms(
                        this@ListMoviesActivity, currentPage + 1
                    )
                }
            }
        })
        binding.listMoviesRV.adapter = playNowAdapter
        binding.listMoviesRV.layoutManager = LinearLayoutManager(this)

        myPresenter.getPlayingNowFilms(this, currentPage)
    }

    private fun callMostPopular() {
        popularAdapter = ListMoviesAdapter(object : ItemClickListener<MovieShowResult> {
            override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                Intent(this@ListMoviesActivity, MovieDetailActivity::class.java).also {
                    it.putExtra(MOVIE_ID_EXTRA, item.id)
                    startActivity(it)
                }
            }
        }, object : PaginationListener {
            override fun onNextPage() {
                if (currentPage < lastPage) {
                    myPresenter.getMostPopularMovies(
                        this@ListMoviesActivity, currentPage + 1
                    )
                }
            }
        })
        binding.listMoviesRV.adapter = popularAdapter
        binding.listMoviesRV.layoutManager = LinearLayoutManager(this)

        myPresenter.getMostPopularMovies(this, currentPage)
    }

    override fun listPopularMovies(
        mostPopularMoviesResponse: List<MovieShowResult>, currentPage: Int, lastPage: Int
    ) {
        this.currentPage = currentPage
        this.lastPage = lastPage

        if (currentPage > 1) {
            popularAdapter.addList(mostPopularMoviesResponse)
        } else {
            popularAdapter.updateList(mostPopularMoviesResponse)
        }
    }

    override fun listPlayingNow(
        playingNowMoviesResponse: List<MovieShowResult>, currentPage: Int, lastPage: Int
    ) {
        this.currentPage = currentPage
        this.lastPage = lastPage

        if (currentPage > 1) {
            playNowAdapter.addList(playingNowMoviesResponse)
        } else {
            playNowAdapter.updateList(playingNowMoviesResponse)
        }
    }

    override fun listBestSeries(
        bestSeriesResult: List<MovieShowResult>, currentPage: Int, lastPage: Int
    ) {
        this.currentPage = currentPage
        this.lastPage = lastPage

        if (currentPage > 1) {
            bestSeriesAdapter.addList(bestSeriesResult)
        } else {
            bestSeriesAdapter.updateList(bestSeriesResult)
        }
    }
}