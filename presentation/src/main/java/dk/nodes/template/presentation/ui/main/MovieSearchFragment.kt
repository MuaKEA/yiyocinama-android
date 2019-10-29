package dk.nodes.template.presentation.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.movieDetails.ShowMovieDetailsActivity
import kotlinx.android.synthetic.main.fragment_movie_search.*
import net.hockeyapp.android.UpdateManager
import timber.log.Timber
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.recyclerview.widget.RecyclerView
import dk.nodes.template.presentation.R


class MovieSearchFragment : BaseFragment(), SearchView.OnQueryTextListener, OnTouchListener {

    private val viewModel by viewModel<MainActivityViewModel>()
    private var adapter: MoviesAdapter? = null
    private var listener: OnFragmentInteractionListener? = null
    private var mainContext : Context?=null
    private var oldQuaryLength=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_search, container, false)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        adapter = MoviesAdapter(context,R.layout.movie_recylerview_row)
        mainContext=context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isDeviceOnlineCheck()
        viewModel.getRecommendations()

        viewModel.viewState.observeNonNull(this) { state ->
            handleMovies(state)
            handleErrors(state)

        }

        input_search.isIconified()
        adapter?.notifyDataSetChanged()
        input_search.setIconified(true)
        input_search.setOnQueryTextListener(this)
        rv_moviesList.setOnTouchListener(this)

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
 }


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
            adapter?.addMovies(movieList)
            adapter?.notifyDataSetChanged()
            updateRecyclerview()
        }
    }



    private fun handleErrors(viewState: MainActivityViewState) {
        viewState.viewError?.let {
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
        showDialog()

    }

    override fun onDestroy() {
        super.onDestroy()
        UpdateManager.unregister()
    }

    private fun showDialog() {
        adapter?.onItemClickedListener = { movie ->
          val intent = Intent(mainContext, ShowMovieDetailsActivity::class.java)
            intent.putExtra("movie" ,movie)
           startActivity(intent)

        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        input_search.clearFocus()
        return false

    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText?.length!! > oldQuaryLength.plus(2)) {
            Log.d("quary old", oldQuaryLength.toString() + "<-old-new ->" + newText?.length?.plus(3).toString())

            input_search.clearFocus()

        }
        oldQuaryLength = newText.length
        newText?.let { viewModel.moviesFun(it) }
        updateRecyclerview()

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        input_search.clearFocus()
        return true
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("movieSearchtxt",input_search.query.toString())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if(savedInstanceState != null){
            val s  = savedInstanceState.getString("movieSearchtxt","")
            input_search.setQuery(s,true)
            input_search.isFocusable = true
            input_search.setQuery(savedInstanceState.getString("movieSearchtxt",""),true)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecommendations()

    }
}



