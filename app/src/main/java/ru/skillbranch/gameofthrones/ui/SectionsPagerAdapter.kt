package ru.skillbranch.gameofthrones.ui

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.skillbranch.gameofthrones.AppConfig


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<HouseFragment>()

    init {
        for(index in 0..6){
            fragmentList.add(HouseFragment.newInstance(AppConfig.getNameByPagerPasition(index)))
        }
    }


    override fun getItem(position: Int): HouseFragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return AppConfig.getNameByPagerPasition(position)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }



}