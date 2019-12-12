package ru.skillbranch.gameofthrones.ui.adapters

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.skillbranch.gameofthrones.ui.HouseFragment


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val names: List<String> ) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<HouseFragment>()

    init {
        for(name in names){
            fragmentList.add(
                HouseFragment.newInstance(
                    name
                )
            )
        }
    }


    override fun getItem(position: Int): HouseFragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return names[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }



}