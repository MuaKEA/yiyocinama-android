package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.LinearLayoutManager
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.hockeyapp.android.UpdateManager
import kotlin.collections.ArrayList

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


    }

    override fun onDestroy() {
        super.onDestroy()
        // If we checked for hockey updates, unregister
        UpdateManager.unregister()
    }

}


//
//        viewModel.viewState.observeNonNull(this) { state ->
//            handleNStack(state)
//        }
//    }
//
//    private fun handleNStack(viewState: MainActivityViewState) {
//        viewState.nstackMessage?.let { showMessageDialog(it) }
//        viewState.nstackRateReminder?.let { showRateReminderDialog(it) }
//        viewState.nstackUpdate?.let { showChangelogDialog(it) }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        // If we checked for hockey updates, unregister
//        UpdateManager.unregister()
//    }


//        input_search.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(s: CharSequence, start: Int,
//                                           count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int,
//                                       before: Int, count: Int) {
//
//
//
//            }
//        })









