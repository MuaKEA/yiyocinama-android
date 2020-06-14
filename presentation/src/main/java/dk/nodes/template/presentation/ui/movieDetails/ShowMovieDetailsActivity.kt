package dk.nodes.template.presentation.ui.movieDetails

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.main.MoviesAdapter
import kotlinx.android.synthetic.main.activity_show_movie_details.*
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class ShowMovieDetailsActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    private val viewModel by viewModel<ShowMovieDetailsViewModel>()
    private var movie: Movie? = null
    private var movieTrailer: String? = null
    lateinit var movieImageView: ImageView
    private var adapter: MoviesAdapter? = null
    var id = ""
    private val imageCallback = object : Target {

        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            Timber.e(errorDrawable.toString() + "<---")

        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

        }


        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            Timber.e("onBitmapLoaded doing something ")
            movieImageView.setImageBitmap(bitmap)
            Palette.from(bitmap)
                    .generate(Palette.PaletteAsyncListener { palette ->

                        movieBackgroundLayout.setBackgroundColor(
                                palette?.darkMutedSwatch?.rgb ?: palette?.darkVibrantSwatch?.rgb
                                ?: palette?.mutedSwatch?.rgb ?: R.color.hockeyapp_text_black
                        )
                    })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_movie_details)
        movieImageView = movie_images

        playbuttonview.setOnClickListener(this)

        val intent = intent
        if (intent != null) {
            movie = intent.getParcelableExtra("movie")
            movie?.id?.let {
                viewModel.fetchThrillerUrl(it)
                id =it
            }
            viewModel.movieSavedCheck(movie!!)

        }

        adapter = MoviesAdapter(this, R.layout.recommendedmovies_row)
       Log.d("heelo", id)
        viewModel.getSemiliarMovies(id)


        movie_images.setOnClickListener(this)
        movieDetails(movie)

        viewModel.viewState.observeNonNull(this) { state ->
            handleThrillerUrl(state)
            handleSemilarMovies(state)
            handleSavedMovies(state)
        }

        adapter?.notifyDataSetChanged()

    }


    private fun addItemOnclick() {
        adapter?.onItemClickedListener = { movie ->
            val intent = Intent(this, ShowMovieDetailsActivity::class.java)
            intent.putExtra("movie", movie)
           intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(intent)

        }
    }


    private fun handleSemilarMovies(viewState: ShowMovieDetailsViewState) {
        viewState.semilarMoivesList?.let { semilarMovies ->
            adapter?.addMovies(semilarMovies)

            addItemOnclick()
            updateRecyclerview()
            adapter?.notifyDataSetChanged()

        }

    }

    fun updateRecyclerview() {
        // Creates a vertical Layout Manager
        showdetailsrecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        showdetailsrecycler.adapter = adapter
        addItemOnclick()


    }

    private fun handleThrillerUrl(viewState: ShowMovieDetailsViewState) {
        viewState.let { fetchurl ->
            Timber.e(fetchurl.thrillerurl)
            movieTrailer = fetchurl.thrillerurl

        }
    }

    fun movieDetails(movie: Movie?) {
        if (movie != null) {

            moviename_txt.setText(movie.name)

            Picasso.get().isLoggingEnabled = true

            Picasso.get().load("https://image.tmdb.org/t/p/original" + movie.poster_path)
                    .error(R.drawable.images)
                    .into(imageCallback)

            viewModel.movieSavedCheck(movie)


            language_txt.setText(movie.original_language)

            if (movie.releaseDate == "") {
                release_txt.setText("Unknown")

            } else {

                val localdate = LocalDate.of(movie.releaseDate?.substring(0, 4)?.toInt()!!, movie.releaseDate!!.substring(5, 7).toInt(), movie.releaseDate!!.substring(8, 10).toInt()).format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
                release_txt.setText(localdate.toString())
            }

            vote_average_txt.setText(movie.vote_average.toString() + "/10")
            overview_txt.setText(movie.overview)
            popularity.setText(movie.popularity.toString())

            save_movie_switch.setOnCheckedChangeListener(this)
        }
    }


    fun showMessage(savemovieswitch: Switch) {
        if (savemovieswitch.isChecked) {
            Toast.makeText(this, "movie is saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "movie is unsaved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val movie = movie ?: return
        if (isChecked) {
            viewModel.saveMovie(movie)
            showMessage(save_movie_switch)


        } else {

            viewModel.deleteMovie(movie)
            showMessage(save_movie_switch)
            viewModel.movieSavedCheck(movie)

        }
    }


    private fun handleSavedMovies(viewState: ShowMovieDetailsViewState) {
        viewState.let { isMovieSaved ->
            if (viewState.isMovieSaved) {
                save_movie_switch.isChecked = true
                Timber.e(isMovieSaved.toString())

            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == playbuttonview.id) {
            v.visibility = View.INVISIBLE
            movie_images.visibility = View.INVISIBLE
            playTrailer()

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        youtube_player_view.release()
    }

    fun playTrailer() {
        youtube_player_view.visibility = View.VISIBLE
        val youTubePlayerView = youtube_player_view
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {

            var videoduration = 0f


            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(movieTrailer!!, 0F)
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


            }

            override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError) {
                super.onError(youTubePlayer, error)

                youtube_player_view.release()
                youtube_player_view.visibility = View.INVISIBLE
                playbuttonview.visibility = View.GONE
                movie_images.visibility = View.VISIBLE
                playbuttonview.isClickable = false
                Toast.makeText(this@ShowMovieDetailsActivity, "No Trailer found", Toast.LENGTH_LONG).show()

            }


            override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                super.onVideoDuration(youTubePlayer, duration)
                videoduration = duration
            }

            override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                super.onCurrentSecond(youTubePlayer, second)

                if (second >= videoduration) {
                    youtube_player_view.visibility = View.INVISIBLE
                    playbuttonview.visibility = View.VISIBLE
                    movie_images.visibility = View.VISIBLE
                }

            }


        })

    }



}



