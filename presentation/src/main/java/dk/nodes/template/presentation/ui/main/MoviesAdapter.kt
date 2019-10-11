package dk.nodes.template.presentation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R

import kotlinx.android.synthetic.main.movie_recylerview_row.view.*
import timber.log.Timber


class MoviesAdapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    var onItemClickedListener: ((movie: Movie) -> Unit?)? = null


    val movies: ArrayList<Movie> = ArrayList()
    // Gets the number of movies in the list
    override fun getItemCount(): Int {
        return movies.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.movie_recylerview_row, parent, false))
    }

    // Binds each movies in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val picasso = Picasso.get()
        picasso.load("https://image.tmdb.org/t/p/original/" + movies.get(position).poster_path)
                .error(R.drawable.images).fit().into(holder.moviePhoto)
        holder.moviename?.text = movies.get(position).name

        holder.root.setOnClickListener {
            onItemClickedListener?.invoke(movies.get(position))
        }

    }

    fun addMovies(list: ArrayList<Movie>) {
        movies.clear()
        Timber.e(list.toString())
        movies.addAll(list)
    }
}


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each movies to
    val moviePhoto = itemView.movieImage
    val root = view.movie_item
    val moviename = view.moviename


}


