package dk.nodes.template.presentation.ui.savedmovies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.R
import kotlinx.android.synthetic.main.recyclerview.view.*
import kotlinx.android.synthetic.main.savedmovie_recyclerview.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class SavedMoviesAdapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    var onItemClickedListener: ((movie: Int) -> Unit?)? = null

    val movies: ArrayList<Movie> = ArrayList()
    // Gets the number of movies in the list

    override fun getItemCount(): Int {
        return movies.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.savedmovie_recyclerview, parent, false))
    }

    // Binds each movies in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var simpledatetimeformatter = SimpleDateFormat("dd-MM-yyyy")




        holder.moviename?.text = movies.get(position).name + " (" + simpledatetimeformatter.format(movies.get(position).releaseDate) + ")"
        holder.overview?.text = movies.get(position).overview

        val picasso = Picasso.get()
        picasso.load("https://image.tmdb.org/t/p/w185/" + movies.get(position).poster_path).fit().into(holder.moviePhoto)

        holder.binview.setOnClickListener {
            onItemClickedListener?.invoke(position)

        }
    }

    fun addMovies(list: ArrayList<Movie>) {
        movies.addAll(list)
    }
}



class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each movies to
    val moviename = view.moviename_txt
    val moviePhoto = itemView.movie_images
    val overview = view.overview_txt
    val binview = view.binview
    // val root = view.movie_item

}
//    @SerializedName("original_title")
//    var name: String,
//    @SerializedName("original_language")
//    var original_language: String,
//    @SerializedName("release_date")
//    var releaseDate: String,
//    @SerializedName("popularity")
//    var popularity: String,
//    @SerializedName("poster_path")
//    var poster_path: String,
//    @SerializedName("vote_average")
//    var vote_average: String,
//    @SerializedName("overview")
//    var overview: String,
//    @SerializedName("id")
//    var id: String





