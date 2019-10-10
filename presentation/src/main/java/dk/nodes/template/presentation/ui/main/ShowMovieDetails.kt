package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import com.squareup.picasso.Picasso
import dk.nodes.template.models.Movie

import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_movie_details.*
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private const val ARG_PARAM1 = "movie"


class ShowMovieDetails : BaseFragment() {
    private val viewModel by viewModel<MainActivityViewModel>()
    private var moviePosition : Int = 0
    private var listener: OnFragmentInteractionListener? = null
    private var movie : Movie? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observeNonNull(this) { state ->
            handleMovies(state)
        }
    }


    private fun handleMovies(viewState: MainActivityViewState) {
        viewState.movies?.let { movieList ->
            Timber.e(movieList.toString())

        }
    }




    fun moveDetails(Movie : Movie){

        moviename_txt.setText(movie?.name)
        Picasso.get().load("https://image.tmdb.org/t/p/w185/" + movie?.poster_path).error(R.drawable.images).fit().into(movie_images)
        language_txt.setText(movie?.original_language)

        if (release_txt.text == "") {
            release_txt.setText("Unknown")

        } else {

            val localdate = LocalDate.of(movie?.releaseDate?.substring(0, 4)?.toInt()!!, movie!!.releaseDate!!.substring(5, 7).toInt(), movie!!.releaseDate!!.substring(8, 10).toInt()).format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))
            release_txt.setText(localdate.toString())
        }

        vote_average_txt.setText(movie?.vote_average.toString() + "/10")
        overview_txt.setText(movie?.overview)
        popularity.setText(movie?.popularity.toString())

        save_movie_switch.setOnClickListener {
            if (!save_movie_switch.isChecked) {

                viewModel.deleteMovie(movie!!)
                showMessage(save_movie_switch)

            } else {

                viewModel.saveMovie(movie!!)
                showMessage(save_movie_switch)

            }
        }

    }

    fun showMessage(savemovieswitch: Switch) {
        if (savemovieswitch.isChecked) {
            Toast.makeText(context, "movie is saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "movie is unsaved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_movie_details, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShowMovieDetails.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(movie: Movie) =
                ShowMovieDetails().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_PARAM1, movie)
                    }
                }
    }
}
