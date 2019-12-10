package ru.skillbranch.gameofthrones.ui

import android.animation.ArgbEvaluator
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_characters_list_screen.*
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R


class CharactersListScreen : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private val argbEvaluator: ArgbEvaluator = ArgbEvaluator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_list_screen)
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )

        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        view_pager.addOnPageChangeListener(this)
        view_pager.currentItem = 0

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
        val startColor: Int = AppConfig.getColorByPosition(position)
        val endColor: Int = AppConfig.getColorByPosition(position+1)
        return argbEvaluator.evaluate(positionOffset, startColor, endColor) as Int
    }
}
