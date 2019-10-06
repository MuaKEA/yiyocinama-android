package dk.nodes.template.presentation.ui.savedmovies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import kotlinx.android.synthetic.main.savedmovie_recyclerview_row.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class SavedMoviesAdapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    var onItemClickedListener: ((movie: Int) -> Unit?)? = null

    val movies: ArrayList<Movie> = ArrayList()
    // Gets the number of movies in the list

    override fun getItemCount(): Int {
        return movies.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.savedmovie_recyclerview_row, parent, false))
    }

    // Binds each movies in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var releaseDate = movies.get(position).releaseDate
        LocalDate.of(releaseDate!!.substring(0, 4).toInt(), releaseDate.substring(5, 7).toInt(), releaseDate.substring(8, 10).toInt()).format((DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)))



        holder.moviename?.text = movies.get(position).name + " (" + releaseDate.toString() + ")"
        holder.overview?.text = movies.get(position).overview

        val picasso = Picasso.get()
        picasso.load("https://image.tmdb.org/t/p/w185/" + movies.get(position).poster_path).centerInside().fit().into(holder.moviePhoto)

        holder.binview.setOnClickListener {
            onItemClickedListener?.invoke(position)

        }
    }

    fun addMovies(list: ArrayList<Movie>) {
        movies.clear()
        movies.addAll(list)
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each movies to
    val moviename = view.moviename_txt
    val moviePhoto = itemView.movie_images
    val overview = view.overview_txt
    val binview = view.binview

}






