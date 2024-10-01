package com.bilalmirza.criticine.activities.mainActivity.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bilalmirza.criticine.R
import com.bilalmirza.criticine.activities.listMovies.ListMoviesActivity
import com.bilalmirza.criticine.activities.movieDetail.MovieDetailActivity
import com.bilalmirza.criticine.databinding.FragmentHomeBinding
import com.bilalmirza.criticine.interfaces.ItemClickListener
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bilalmirza.criticine.utils.Constants
import com.bilalmirza.criticine.utils.Constants.MOVIE_ID_EXTRA
import com.bilalmirza.criticine.utils.Constants.TYPE_BEST_SERIES
import com.bilalmirza.criticine.utils.Constants.TYPE_PLAYING_NOW
import com.bilalmirza.criticine.utils.Constants.TYPE_POPULAR
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeFragment : Fragment(R.layout.fragment_home), HomeFragmentView {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var myPresenter: HomeFragmentPresenter
    private lateinit var myPopularFilmsAdapter: HomeFragmentListingAdapter
    private lateinit var myPlayingNowMiesAdapter: HomeFragmentListingAdapter
    private lateinit var myBestSeriesAdapter: HomeFragmentListingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        //  initialising the presenter
        myPresenter = HomeFragmentPresenter(this)
        //  setting up click listeners
        clickListeners()
        //  setting up the horizontal films recycler view
        horizontalMoviesRV()
        //  calling api for horizontal movie posters
        initHorizontalFilms()
    }

    override fun onBestSeries(bestSeries: List<MovieShowResult>) {
        myBestSeriesAdapter.updateList(bestSeries)
    }

    private fun horizontalMoviesRV() {
        myPopularFilmsAdapter =
            HomeFragmentListingAdapter(object : ItemClickListener<MovieShowResult> {
                override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                    Intent(requireContext(), MovieDetailActivity::class.java).also {
                        it.putExtra(MOVIE_ID_EXTRA, item.id)
                        startActivity(it)
                    }
                }
            })
        binding.popularMovieScrollRV.adapter = myPopularFilmsAdapter
        binding.popularMovieScrollRV.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        myPlayingNowMiesAdapter =
            HomeFragmentListingAdapter(object : ItemClickListener<MovieShowResult> {
                override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                    Intent(requireContext(), MovieDetailActivity::class.java).also {
                        it.putExtra(MOVIE_ID_EXTRA, item.id)
                        startActivity(it)
                    }
                }
            })
        binding.playingNowMovieScrollRV.adapter = myPlayingNowMiesAdapter
        binding.playingNowMovieScrollRV.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        myBestSeriesAdapter =
            HomeFragmentListingAdapter(object : ItemClickListener<MovieShowResult> {
                override fun onItemClick(item: MovieShowResult, pos: Int, type: Int) {
                    Intent(requireContext(), MovieDetailActivity::class.java).also {
                        it.putExtra(MOVIE_ID_EXTRA, item.id)
                        it.putExtra(Constants.IS_SERIES_EXTRAS, true)
                        startActivity(it)
                    }
                }
            })
        binding.bestSeriesScrollRV.adapter = myBestSeriesAdapter
        binding.bestSeriesScrollRV.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    }

    private fun initHorizontalFilms() {
        myPresenter.getMostPopularMovies(requireContext())
        myPresenter.getComingSoonImages(requireContext())
        myPresenter.getPlayingNowFilms(requireContext())
        myPresenter.getBestSeries(requireContext())
    }

    override fun onPlayingNowMovies(playingNowMoviesResponse: List<MovieShowResult>) {
        myPlayingNowMiesAdapter.updateList(playingNowMoviesResponse)
    }

    private fun clickListeners() {
        binding.seeAllTV1.setOnClickListener {
            Intent(activity, ListMoviesActivity::class.java).also {
                it.putExtra(Constants.MOVIE_TYPE_EXTRAS, TYPE_POPULAR)
                startActivity(it)
            }
        }
        binding.seeAllTV2.setOnClickListener {
            Intent(activity, ListMoviesActivity::class.java).also {
                it.putExtra(Constants.MOVIE_TYPE_EXTRAS, TYPE_PLAYING_NOW)
                startActivity(it)
            }
        }
        binding.seeAllTV3.setOnClickListener {
            Intent(activity, ListMoviesActivity::class.java).also {
                it.putExtra(Constants.MOVIE_TYPE_EXTRAS, TYPE_BEST_SERIES)
                startActivity(it)
            }
        }
    }

    private fun imageSlider(listOfImages: List<SlideModel>) {
        binding.myImageSlider.setImageList(listOfImages, ScaleTypes.FIT)
        binding.myImageSlider.setSlideAnimation(AnimationTypes.BACKGROUND_TO_FOREGROUND)
    }

    override fun onMostPopularMovies(mostPopularResponse: List<MovieShowResult>) {
        myPopularFilmsAdapter.updateList(mostPopularResponse)
    }

    override fun onComingSoonImages(comingSoonImages: List<MovieShowResult>) {
        val listOfUrls = comingSoonImages.map {
            SlideModel("https://image.tmdb.org/t/p/w500${it.backdropPath}")
        }
        imageSlider(listOfUrls.take(5))
    }
}