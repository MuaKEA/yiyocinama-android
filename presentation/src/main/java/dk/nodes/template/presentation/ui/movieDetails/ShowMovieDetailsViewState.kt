package dk.nodes.template.presentation.ui.movieDetails

import dk.nodes.nstack.kotlin.models.AppUpdate
import dk.nodes.nstack.kotlin.models.Message
import dk.nodes.nstack.kotlin.models.RateReminder
import dk.nodes.template.models.Movie
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewError

data class ShowMovieDetailsViewState (

            val errorMessage: SingleEvent<String>? = null,
            val isLoading: Boolean = false,
            val nstackMessage: Message? = null,
            val nstackRateReminder: RateReminder? = null,
            val nstackUpdate: AppUpdate? = null,
            val viewError: SingleEvent<ViewError>? = null,
            val thrillerurl : String?=null,
            var isMovieSaved : Boolean=false,
            val movies: ArrayList<Movie>? = null,
            val semilarMoivesList : ArrayList<Movie>?=null




            )

