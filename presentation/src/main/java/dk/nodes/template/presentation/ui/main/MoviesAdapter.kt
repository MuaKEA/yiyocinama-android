package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import kotlinx.android.synthetic.main.recyclerview.view.*


class MoviesAdapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    val movies: ArrayList<Movie> = ArrayList()

    // Gets the number of movies in the list
    override fun getItemCount(): Int {
        return movies.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview, parent, false))
    }

    // Binds each movies in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.moviename?.text = movies.get(position).name
        //  holder.genre?.text = movies.get(position).genre
        holder.releaseDate?.text = movies.get(position).releaseDate
        holder.popularity?.text = movies.get(position).popularity
    }

    fun addMovies(list: ArrayList<Movie>) {


            movies.addAll(list)
        }
    }





class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each movies to
    val moviename = view.movieName
    //val genre = view.genre
    val releaseDate = view.relasedate
    val popularity = view.popularity
}
