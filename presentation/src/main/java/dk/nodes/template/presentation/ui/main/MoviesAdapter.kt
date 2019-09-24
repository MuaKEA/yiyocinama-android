package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import kotlinx.android.synthetic.main.recyclerview.view.*
import timber.log.Timber




class MoviesAdapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    var onclikposition  : Int = 0
    var onItemClickedListener: ((movie: Movie) ->Unit?)? = null


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
        val picasso = Picasso.get()
        picasso.load("https://image.tmdb.org/t/p/w185/" + movies.get(position).poster_path).fit().into(holder.moviePhoto)
        holder.root.setOnClickListener {
            onItemClickedListener?.invoke(movies.get(position))
        }

    }

    fun addMovies(list: ArrayList<Movie>) {
        movies.clear()
        movies.addAll(list)
    }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view),View.OnClickListener {
    // Holds the TextView that will add each movies to
    val moviename = view.movieName
    val moviePhoto = itemView.movieImage
    val root = view.movie_item

    init {
        view.setOnClickListener(this)
    }

    //4
    override fun onClick(v: View) {
        val pos = layoutPosition


    }


}


