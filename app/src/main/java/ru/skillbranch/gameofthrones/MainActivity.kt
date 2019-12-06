package ru.skillbranch.gameofthrones

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.repositories.RootRepository

class MainActivity : AppCompatActivity() {

    private lateinit var houses: List<HouseRes>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        setContentView(R.layout.activity_main)
        splash.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY )
        RootRepository.getAllHouses {
            houses = it
        }
    }
}
