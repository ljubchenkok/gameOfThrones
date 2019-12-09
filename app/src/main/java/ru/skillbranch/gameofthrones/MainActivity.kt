package ru.skillbranch.gameofthrones

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.repositories.RootRepository

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)
        setContentView(R.layout.activity_main)
        splash.getDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY )
        RootRepository.getNeedHouses(*AppConfig.NEED_HOUSES) {
            RootRepository.insertHouses(it){
                Log.d("DB","Done!!!")
            }
        }
    }
}
