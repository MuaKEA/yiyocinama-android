package dk.nodes.template.presentation.ui.experimental.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import dk.nodes.template.presentation.R

private val TAB_TITLES = arrayOf(
        R.string.tab_text_0,
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_4,
        R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentStatePagerAdapter(fm) {



    var moviesSearhTxt: String = ""



    fun addString(movieName : String) {

        moviesSearhTxt= movieName

    }

    override fun getItemPosition(`object`: Any): Int {

        return POSITION_NONE;
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0->return MovieSearchFragment.newInstance(moviesSearhTxt,MovieViewType.Recommended)
            1-> return MovieSearchFragment.newInstance(moviesSearhTxt,MovieViewType.Movie)
            2->return MovieSearchFragment.newInstance(moviesSearhTxt,MovieViewType.ActionMovie)
            3->return MovieSearchFragment.newInstance(moviesSearhTxt,MovieViewType.DramaMovie)
            4->return MovieSearchFragment.newInstance(moviesSearhTxt,MovieViewType.ComedyMovie)
            5->return MovieSearchFragment.newInstance(moviesSearhTxt,MovieViewType.HorrorMovie)


        }


        return MovieSearchFragment.newInstance(moviesSearhTxt,MovieViewType.Recommended)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 6 total pages.
        return 6
    }
}


sealed class MovieViewType {

    object Recommended: MovieViewType()
    object Movie : MovieViewType()
    object ActionMovie :MovieViewType()
    object DramaMovie : MovieViewType()
    object ComedyMovie : MovieViewType()
    object HorrorMovie : MovieViewType()
}