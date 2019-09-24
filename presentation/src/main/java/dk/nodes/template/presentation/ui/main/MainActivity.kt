package dk.nodes.template.presentation.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movieinfodiaglogview.*
import net.hockeyapp.android.UpdateManager

class MainActivity : BaseActivity() {


    private val viewModel by viewModel<MainActivityViewModel>()
    private val adapter = MoviesAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setupRecyclerview()
        viewModel.viewState.observeNonNull(this) { state ->
            handleNStack(state)
        }


        input_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {


                viewModel.moviefun(s.toString())
                setupRecyclerview()

            }
        })
    }

    private fun handleNStack(viewState: MainActivityViewState) {
        viewState.movies?.let { movieList ->
            adapter.addMovies(movieList)
            adapter.notifyDataSetChanged()

        }
    }

    private fun setupRecyclerview() {
        // Creates a vertical Layout Manager
        rv_moviesList.layoutManager = LinearLayoutManager(this)


        // Access the RecyclerView Adapter and load the data into it


        rv_moviesList.adapter = adapter
        rv_moviesList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        showDialog("titel")
    }


    override fun onDestroy() {
        super.onDestroy()
        // If we checked for hockey updates, unregister
        UpdateManager.unregister()
    }






            fun showDialog(title: String) {


                 adapter.onItemClickedListener = { movie ->
                     println("test123 $movie")

                     val dialog = Dialog(this)
                     dialog.requestWindowFeature(Window.DECOR_CAPTION_SHADE_DARK)
                     dialog.setCancelable(false)
                     dialog.setContentView(R.layout.movieinfodiaglogview)
                     val moveName= dialog.findViewById<TextView>(R.id.moviename_txt)
                     moveName.setText(movie.name)

                     val photo = dialog.movie_image
                     Picasso.get().load("https://image.tmdb.org/t/p/w185/"+ movie.poster_path).into(photo)
                     // val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
                     // val noBtn = dialog.findViewById(R.id.noBtn) as TextView
                     //yesBtn.setOnClickListener {
                     //   dialog.dismiss()
                     //}
                     //noBtn.setOnClickListener { dialog.dismiss() }
                     dialog.show()
                 }
            }
        }














