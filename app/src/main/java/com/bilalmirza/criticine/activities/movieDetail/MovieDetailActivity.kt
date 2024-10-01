package com.bilalmirza.criticine.activities.movieDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isVisible
import com.bilalmirza.criticine.databinding.ActivityMovieDetailBinding
import com.bilalmirza.criticine.model.movieShow.MovieShowResult
import com.bilalmirza.criticine.utils.Constants
import com.bilalmirza.criticine.utils.Constants.MOVIE_ID_EXTRA
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.tabs.TabLayout
import java.util.UUID

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var myAdapter: GenreAdapter
    private lateinit var viewPagerAdapter: MovieDetailViewPagerAdapter
    private lateinit var myPresenter: MovieDetailPresenter
    private var showResult: MovieShowResult? = null
    private var mediaName = ""

    companion object {
        var movieID = 0
        var isSeries = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  initialising the presenter
        myPresenter = MovieDetailPresenter(this)
        //  receiving data
        receiveData()
        //initialising view pager
        initViewPager()
        //  changing color of status bar text programmatically
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
        //  setting click listeners
        clickListeners()
        //  setting up genre recycler view
        genreRecyclerView()
    }

    private fun genreRecyclerView() {
        val myLayoutManager = FlexboxLayoutManager(this)
        myLayoutManager.justifyContent = JustifyContent.FLEX_START
        myLayoutManager.flexDirection = FlexDirection.ROW

        myAdapter = GenreAdapter()
        binding.recylerViewRV.adapter = myAdapter
        binding.recylerViewRV.layoutManager = myLayoutManager
    }

    @SuppressLint("CommitTransaction")
    private fun receiveData() {
        movieID = intent.getIntExtra(MOVIE_ID_EXTRA, 0)
        isSeries = intent.getBooleanExtra(Constants.IS_SERIES_EXTRAS, false)

        if (isSeries) {
            myPresenter.getSeriesDetails(movieID, this)
        } else {
            myPresenter.getDetailsByID(movieID, this)
        }
    }

    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onMovieDetail(responseMoviesResult: MovieShowResult) {
        this.showResult = responseMoviesResult

        mediaName = responseMoviesResult.title!!

        binding.filmTitle.text = responseMoviesResult.title
        Glide.with(this).load("https://image.tmdb.org/t/p/w500${responseMoviesResult.backdropPath}")
            .into(binding.detailFilmBackgroundIV)
        Glide.with(this).load("https://image.tmdb.org/t/p/w500${responseMoviesResult.posterPath}")
            .into(binding.horizontalMovieScrollIV)

        val (hours, minutes) = convertMinutesToHoursAndMinutes(responseMoviesResult.runtime)
        binding.flimDuration.text = "$hours hours $minutes minute(s)"

        val doubleRating = String.format("%.1f", responseMoviesResult.voteAverage)
        binding.filmRating.text = doubleRating

        binding.overviewTV.text = responseMoviesResult.overview

        responseMoviesResult.genres?.let { myAdapter.updateList(it) }
    }

    @SuppressLint("DefaultLocale")
    override fun onSeriesDetail(responseSeriesResult: MovieShowResult) {
        this.showResult = responseSeriesResult

        binding.filmTitle.text = responseSeriesResult.name

        binding.overviewTV.text = responseSeriesResult.overview

        val doubleRating = String.format("%.1f", responseSeriesResult.voteAverage)
        binding.filmRating.text = doubleRating

        binding.flimDuration.visibility = View.GONE

        Glide.with(this).load("https://image.tmdb.org/t/p/w500${responseSeriesResult.backdropPath}")
            .into(binding.detailFilmBackgroundIV)
        Glide.with(this).load("https://image.tmdb.org/t/p/w500${responseSeriesResult.posterPath}")
            .into(binding.horizontalMovieScrollIV)

        responseSeriesResult.genres?.let { myAdapter.updateList(it) }
    }

    override fun onSuccessSave(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun convertMinutesToHoursAndMinutes(totalMinutes: Int): Pair<Int, Int> {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        return Pair(hours, minutes)
    }

    private fun clickListeners() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                renderTab(binding.tabLayout.selectedTabPosition)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        binding.saveFilmBtn.setOnClickListener {
            if (showResult == null) return@setOnClickListener
            binding.saveFilmBtn.isEnabled = false
            myPresenter.saveFilm(
                this,
                genUUID(),
                showResult!!.id,
                showResult!!.posterPath,
                showResult!!.voteAverage,
                showResult!!.overview,
                isSeries,
                if (isSeries) showResult!!.name ?: "" else showResult!!.title ?: ""
            )
            binding.saveFilmBtn.isEnabled = true
        }
        binding.addReviewBTN.setOnClickListener {
            if (isSeries) {
                val uri = Uri.parse("https://www.themoviedb.org/tv/$movieID-$mediaName/reviews")
                startActivity(uri)
            } else {
                val uri = Uri.parse("https://www.themoviedb.org/movie/$movieID-$mediaName/reviews")
                startActivity(uri)
            }
        }
    }

    fun startActivity(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    private fun renderTab(position: Int) {
        binding.addReviewBTN.isVisible = position == 1
        binding.movieInformationViewPager.currentItem = position
    }

    private fun initViewPager() {
        viewPagerAdapter = MovieDetailViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.movieInformationViewPager.adapter = viewPagerAdapter
        binding.movieInformationViewPager.isUserInputEnabled = false
    }

    private fun genUUID(): String {
        val myUUID = UUID.randomUUID()
        val myUUIDAsString = myUUID.toString()

        return myUUIDAsString
    }
}