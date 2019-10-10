package dk.nodes.template.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.hockeyapp.android.UpdateManager
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieActivity
import timber.log.Timber


class MainActivity : BaseActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().add(R.id.main_frame,MovieSearchFragment.newInstance(),"").commit()


    }
}

    private fun showDialog() {

       // adapter.onItemClickedListener = { movie ->
         //   Timber.e(movie.toString())
        }            //            val dialog = Dialog(this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
//            dialog.setCancelable(true)
//            dialog.setContentView(R.layout.movieinfodiaglogview)
//            dialog.moviename_txt.setText(movie.name)
//            Picasso.get().load("https://image.tmdb.org/t/p/w185/" + movie.poster_path).error(R.drawable.images).fit().into(dialog.movie_images)
//            dialog.language_txt.setText(movie.original_language)
//
//            if (dialog.release_txt.text == "") {
//                dialog.release_txt.setText("Unknown")
//
//            } else {
//
//                val localdate = LocalDate.of(movie.releaseDate!!.substring(0, 4).toInt(), movie.releaseDate!!.substring(5, 7).toInt(), movie.releaseDate!!.substring(8, 10).toInt()).format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
//                dialog.release_txt.setText(localdate.toString())
//            }
//
//            dialog.vote_average_txt.setText(movie.vote_average + "/10")
//            dialog.overview_txt.setText(movie.overview)
//            dialog.popularity.setText(movie.popularity)
//            val savemovieswitch = dialog.save_movie_switch
//
//            dialog.show()
//            savemovieswitch.setOnClickListener {
//                if (!savemovieswitch.isChecked) {
//
//                    viewModel.deleteMovie(movie)
//                    showMessage(savemovieswitch)
//
//                } else {
//
//                    viewModel.saveMovie(movie)
//                    showMessage(savemovieswitch)
//
//                }
//            }





















