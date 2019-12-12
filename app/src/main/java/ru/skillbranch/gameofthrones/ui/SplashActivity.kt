package ru.skillbranch.gameofthrones.ui

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.repositories.RootRepository


class SplashActivity : AppCompatActivity() {

    companion object {
        const val DELAY = 100L
    }

    private var isHousesReady = false
    private var isCharactersReady = false
    private val colorAnim = ObjectAnimator.ofFloat(0f, 0.7f)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        setContentView(R.layout.activity_main)
        startAnimateImageView()
//        RootRepository.dropDb { getData() }
        getData()
        checkDataIsReady()
        return


    }

    private fun stopAnimateImageView() {
        runOnUiThread {
            colorAnim.cancel()
        }

    }

    private fun checkDataIsReady() {
        val r = Runnable {
            if (isHousesReady && isCharactersReady) showCharactersListScreen()
            else checkDataIsReady()
        }
        Handler().postDelayed(r, DELAY)


    }

    private fun getData() {
        RootRepository.isNeedUpdate { isNeeded ->
            if (isNeeded) {
                if (!isOnline()) {
                    stopAnimateImageView()
                    Snackbar.make(
                        ll_splash,
                        R.string.no_internet, Snackbar.LENGTH_INDEFINITE
                    )
                        .show();
                    return@isNeedUpdate
                }
                RootRepository.getNeedHouseWithCharacters(
                    *AppConfig.NEED_HOUSES
                ) {
                    RootRepository.insertHouses(it.map { pair -> pair.first }) {
                        isHousesReady = true
                    }
                    for (pair in it) {
                        RootRepository.insertCharacters(pair.second) { isCharactersReady = true }
                    }
                }

            } else {
                isHousesReady = true
                isCharactersReady = true
            }
        }
    }

    private fun showCharactersListScreen() {
        val intent = Intent(this, CharactersListScreen::class.java)
        startActivity(intent)
        finish()
    }


    fun isOnline(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun startAnimateImageView() {
        val red = Color.RED
        colorAnim.addUpdateListener { animation ->
            val mul = animation.animatedValue as Float
            val alphaRed = adjustAlpha(red, mul)
            iv_splash.setColorFilter(alphaRed, PorterDuff.Mode.OVERLAY)
            if (mul.toDouble() == 0.0) {
                iv_splash.setColorFilter(null)
            }
        }
        colorAnim.duration = 1000
        colorAnim.repeatMode = ValueAnimator.REVERSE
        colorAnim.repeatCount = -1
        colorAnim.start()
    }

    fun adjustAlpha(color: Int, factor: Float): Int {
        val alpha = Math.round(Color.alpha(color) * factor)
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return Color.argb(alpha, red, green, blue)
    }

}
