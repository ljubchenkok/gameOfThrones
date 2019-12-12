package ru.skillbranch.gameofthrones.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_character_screen.*
import kotlinx.android.synthetic.main.content_character_screen.*
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull

class CharacterScreen : AppCompatActivity() {

    private var characterId: String? = null
    private lateinit var characterViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_screen)
        setSupportActionBar(toolbar)
        characterId = intent.getStringExtra(HouseFragment.ARG_CHARACTER_ID)
        if(characterId.isNullOrEmpty()) finish()
        initToolbar()
        initViewModel()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    private fun initViewModel() {
        characterViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        characterViewModel.characterFullLiveData.observe(this, Observer {
            setUI(it)

        })
        characterViewModel.getCharacterFullById(characterId!!)

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setUI(character: CharacterFull) {
        val houseColor = AppConfig.getColorByHouseName(character.house)
        val iconColor = AppConfig.getIconColorByHouseName(character.house)
        toolbar_layout.title = character.name
        toolbar_layout.setContentScrimColor(houseColor)
        toolbar_layout.background = getDrawable(
            AppConfig.getBackgroudByHouseName(
                character.house
            )
        )
        toolbar.setBackgroundColor(houseColor)
        ic_words.setColorFilter(iconColor)
        ic_born.setColorFilter(iconColor)
        ic_titles.setColorFilter(iconColor)
        ic_aliases.setColorFilter(iconColor)
        if(character.father != null) {
            btn_father.setBackgroundColor(AppConfig.getColorByHouseName(character.father.house))
            btn_father.setText(character.father.name)
            ic_father.setColorFilter(iconColor)
            btn_father.setOnClickListener { showParent(character.father.id) }
        } else {
            ic_father.visibility = View.GONE
            tv_father_title.visibility = View.GONE
            btn_father.visibility = View.GONE
        }
        if(character.mother != null) {
            btn_mother.setBackgroundColor(AppConfig.getColorByHouseName(character.mother.house))
            btn_mother.setText(character.mother.name)
            ic_mother.setColorFilter(iconColor)
            btn_mother.setOnClickListener { showParent(character.mother.id) }
        } else {
            ic_mother.visibility = View.GONE
            tv_mother_title.visibility = View.GONE
            btn_mother.visibility = View.GONE
        }

        tv_words_text.text = character.words
        tv_born_text.text = character.born
        tv_titles_text.text = character.titles.joinToString(separator = "\n")
        tv_aliases_text.text = character.aliases.joinToString(separator = "\n")
        if(character.died.isNotEmpty()){
            Snackbar.make(scroll_view, "Died In ${character.died}", Snackbar.LENGTH_INDEFINITE)
                .show()
        }

    }

    private fun showParent(id: String) {
        val intent = Intent(this,CharacterScreen::class.java )
        intent.putExtra(HouseFragment.ARG_CHARACTER_ID, id)
        startActivity(intent)
    }
}
