package dk.nodes.template.presentation.ui.movieDetails

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.Toast
import com.bumptech.glide.Glide
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_show_movie_details.*
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class ShowMovieDetailsActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    private val viewModel by viewModel<ShowMovieDetailsViewModel>()
    private var movie: Movie? = null
    private var movieTrailer: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_movie_details)
        playbuttonview.setOnClickListener(this)


        val intent = intent
        if (intent != null) {
            movie = intent.getParcelableExtra("movie")
            viewModel.fetchThrillerUrl(movie?.id!!)
        }

        Timber.e(movie.toString())

        viewModel.viewState.observeNonNull(this) { state ->
            handleSavedMovies(state)
            handleThrillerUrl(state)
        }
        movie_images.setOnClickListener(this)
        viewModel.movieSavedCheck(movie!!)
        movieDetails(movie)
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
        Glide.with(this).load("https://image.tmdb.org/t/p/original/" + movie.poster_path).error(R.drawable.images).into(movie_images)




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
  if(v?.id == playbuttonview.id){
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
        override fun onReady(youTubePlayer: YouTubePlayer) {
            Timber.e(movieTrailer)
            youTubePlayer.loadVideo(movieTrailer!!, 0F)

        }

        override fun onError(youTubePlayer: YouTubePlayer, error: PlayerConstants.PlayerError)  {
            super.onError(youTubePlayer, error)
            youtube_player_view.release()
            youtube_player_view.visibility = View.INVISIBLE
            playbuttonview.visibility = View.GONE
            movie_images.visibility = View.VISIBLE
            playbuttonview.isClickable = false
            Toast.makeText(this@ShowMovieDetailsActivity,"No Trailer found",Toast.LENGTH_LONG).show()

        }

    })

}


}



