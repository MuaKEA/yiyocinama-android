package dk.nodes.template.presentation.ui.experimental.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter



class SectionsPagerAdapter(private val context: Context, fm: FragmentManager,private val TAB_TITLES : Array<Int>,
                           private val instance : ArrayList<Fragment>
)
    : FragmentPagerAdapter(fm) {


    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE;
    }

    override fun getItem(position: Int): Fragment {
        return instance[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return TAB_TITLES.size
    }
}

sealed class MovieViewType  {

    object Recommended:  MovieViewType()
    object Movie:        MovieViewType()
    object ActionMovie:  MovieViewType()
    object DramaMovie:   MovieViewType()
    object ComedyMovie:  MovieViewType()
    object HorrorMovie:  MovieViewType()
    object SavedMovie:   MovieViewType()
    object NowPlaying:   MovieViewType()
    object PopularMovie: MovieViewType()
    object TopRateMovie: MovieViewType()

}
