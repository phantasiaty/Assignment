package com.example.tablayout.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.tablayout.R
import com.example.tablayout.ui.main.Profile.ProfileFragment
import com.example.tablayout.ui.main.Standings.StandingsFragment
import com.example.tablayout.ui.main.Workout.WorkoutFragment

private val TAB_TITLES = arrayOf(
    R.string.tab1,
    R.string.tab2,
    R.string.tab3
)



/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when(position){
            0 ->
            {
                WorkoutFragment()
            }
            1 ->
            {
                StandingsFragment()
            }
            else ->
            {
                ProfileFragment()
            }

        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }



    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}