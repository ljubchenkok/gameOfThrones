package ru.skillbranch.gameofthrones

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.repositories.RootRepository


class MainActivity : AppCompatActivity() {

    var progressFlag = true
    private lateinit var houses: List<Pair<HouseRes, List<CharacterRes>>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        setContentView(R.layout.activity_main)
        if(!isOnline()) {
            Snackbar.make(ll_splash, R.string.no_internet, Snackbar.LENGTH_INDEFINITE)
                .show();
            return
        }
        showProgress()
        RootRepository.isNeedUpdate { isNeeded ->
            if (isNeeded) {
                RootRepository.getNeedHouseWithCharacters(*AppConfig.NEED_HOUSES) {
                    RootRepository.insertHouses(it.map { pair -> pair.first }) { showProgress() }
                    for (pair in it) {
                        RootRepository.insertCharacters(pair.second) { showProgress() }
                    }
                    showCharactersListScreen()

                }

            } else {
                showCharactersListScreen()
            }
        }

    }

    private fun showCharactersListScreen() {
        val intent = Intent(this, CharactersListScreen::class.java)
        startActivity(intent)
        finish()
    }

    fun showProgress() {
        if (progressFlag) {
            iv_splash.drawable.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
        } else {
            iv_splash.drawable.clearColorFilter()
        }
        progressFlag = !progressFlag

    }

    fun isOnline(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

}
