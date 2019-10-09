package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.savedmovies.ShowSavedMovieActivity
import kotlinx.android.synthetic.main.fragment_movie_search.*
import net.hockeyapp.android.UpdateManager
import timber.log.Timber


class MovieSearchFragment : BaseFragment(), SearchView.OnQueryTextListener, BottomNavigationView.OnNavigationItemSelectedListener {


    private val viewModel by viewModel<MainActivityViewModel>()
    private val adapter = MoviesAdapter(context)

    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        val view: View  = inflater.inflate(R.layout.fragment_movie_search, container, false)



        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isDeviceOnlineCheck()
        viewModel.viewState.observeNonNull(this) { state ->
            handleMovies(state)
            handleErrors(state)
        }

        input_search.setOnQueryTextListener(this)

        bottomNavigation_View.setOnNavigationItemSelectedListener(this)
        adapter.notifyDataSetChanged()

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }



//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
                MovieSearchFragment().apply {

                    }
                }


    private fun handleMovies(viewState: MainActivityViewState) {
        viewState.movies?.let { movieList ->
         Timber.e(movieList.toString())
            error_view.visibility = View.INVISIBLE
            adapter.addMovies(movieList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun handleErrors(viewState: MainActivityViewState) {
        viewState?.viewError?.let {
            if (it.consumed) return@let
            Timber.e("no internet")
            Glide.with(this).asGif().load(R.drawable.nointernetconnection).into(error_view)
            error_view.visibility = View.VISIBLE
            Snackbar.make(rv_moviesList, "No Internet OR No Result", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun updateRecyclerview() {
        // Creates a vertical Layout Manager
        rv_moviesList.layoutManager = GridLayoutManager(context, 3)
        // Access the RecyclerView Adapter and load the data into it
        rv_moviesList.adapter = adapter
        rv_moviesList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        showDialog()

    }

    override fun onDestroy() {
        super.onDestroy()

        UpdateManager.unregister()
    }

    private fun showDialog() {

        adapter.onItemClickedListener = { movie ->
            Timber.e(movie.toString())
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



    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.navigation_savedphoto) {

            startActivity(Intent(context, ShowSavedMovieActivity::class.java))
        } else {
            Toast.makeText(context, "Ready in search", Toast.LENGTH_LONG).show()

        }

        return true
    }


    fun showMessage(savemovieswitch: Switch) {
        if (savemovieswitch.isChecked) {
            Toast.makeText(context, "movie is saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "movie is unsaved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.moviesfun(newText.toString())
        updateRecyclerview()

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }


}

