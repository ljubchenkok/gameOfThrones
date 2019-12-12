package ru.skillbranch.gameofthrones.ui


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_house.*
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.ui.adapters.CharactersAdapter
import ru.skillbranch.gameofthrones.ui.custom.SimpleItemDecorator


class HouseFragment : Fragment() {


    lateinit var houseName: String
    private lateinit var houseViewModel: MainViewModel
    private lateinit var charactersAdapter: CharactersAdapter

    companion object {
        const val ARG_HOUSE_NAME = "house_name"
        const val ARG_CHARACTER_ID = "character_id"
        fun newInstance(name: String): HouseFragment {
            val args = Bundle()
            args.putString(ARG_HOUSE_NAME, name)
            val fragment = HouseFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        houseName = arguments?.getString(ARG_HOUSE_NAME) ?: "unknown"
        return inflater.inflate(R.layout.fragment_house, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val searchView = (menu.findItem(R.id.action_search))?.actionView as SearchView
        searchView.queryHint = "Введите имя пользователя"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                houseViewModel.handleSearchQuery(query)
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                houseViewModel.handleSearchQuery(newText)
                return true


            }

        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initViews() {
        val simpleItemDecorator =
            SimpleItemDecorator(context!!)
        charactersAdapter =
            CharactersAdapter() {
                showCharacterDetailScreen(it.id)
            }
        with(rv_characters) {
            adapter = charactersAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            addItemDecoration(simpleItemDecorator)
        }
    }

    private fun showCharacterDetailScreen(id: String) {
        val intent = Intent(context, CharacterScreen::class.java)
        intent.putExtra(ARG_CHARACTER_ID, id)
        startActivity(intent)
    }

    private fun initViewModel() {
        houseViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        houseViewModel.getCharacterItems().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                charactersAdapter.updateData(it)
            }

        })
        houseViewModel.getCharactersByHouseName(houseName)

    }


}
