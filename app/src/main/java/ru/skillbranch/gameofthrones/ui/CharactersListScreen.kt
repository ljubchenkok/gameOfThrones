package ru.skillbranch.gameofthrones.ui

import android.animation.ArgbEvaluator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_characters_list_screen.*
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.ui.adapters.SectionsPagerAdapter


class CharactersListScreen : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private val argbEvaluator: ArgbEvaluator = ArgbEvaluator()

    private lateinit var houseViewModel: MainViewModel
    private lateinit var houseNames: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_list_screen)
        initToolbar()
        initViewModel()


    }

    private fun initToolbar() {
        setSupportActionBar(toolbar_character_list)
    }


    private fun initViewModel() {
        houseViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        houseViewModel.housesLiveData.observe(this, Observer {
            initViews(it)

        })
        houseViewModel.getHouses()
    }

    private fun initViews(houses: List<House>) {
        houseNames = sortHouses(houses.map { it.name })
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager,
                houseNames
            )

        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        view_pager.addOnPageChangeListener(this)
        view_pager.currentItem = 0
    }

    private fun sortHouses(unsortedHouses: List<String>): List<String> {
        val sortedHouses = mutableListOf<String>()
        for (name in AppConfig.NEED_HOUSES) {
            val match = unsortedHouses.find { name.contains(it) }
            if (match != null) sortedHouses += match
        }
        return sortedHouses


    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val color = getHeaderColor(position, positionOffset)
        app_bar.setBackgroundColor(color)
        tabs.setBackgroundColor(color)

    }

    override fun onPageSelected(position: Int) {
    }

    private fun getHeaderColor(position: Int, positionOffset: Float): Int {
        val startColor: Int = AppConfig.getColorByHouseName(houseNames[position])
        val endColor: Int =
            AppConfig.getColorByHouseName(if (position < houseNames.size - 1) houseNames[position + 1] else houseNames[0])
        return argbEvaluator.evaluate(positionOffset, startColor, endColor) as Int
    }
}
