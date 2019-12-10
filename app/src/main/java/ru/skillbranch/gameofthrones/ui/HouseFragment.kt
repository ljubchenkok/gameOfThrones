package ru.skillbranch.gameofthrones.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_house.*
import ru.skillbranch.gameofthrones.R


class HouseFragment : Fragment() {




    lateinit var houseName:String
    private lateinit var mainViewModel: MainViewModel
    private lateinit var charactersAdapter: CharactersAdapter

    companion object{
        const val ARG_NAME = "name"
        fun newInstance(name: String): HouseFragment {
            val args = Bundle()
            args.putString(ARG_NAME, name)
            val fragment = HouseFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        houseName = arguments?.getString(ARG_NAME) ?: "unknown"
        return inflater.inflate(R.layout.fragment_house, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initViewModel()

    }

    private fun initViews() {
        val simpleItemDecorator = SimpleItemDecorator(context!!)
        charactersAdapter = CharactersAdapter()
        with(rv_characters) {
            adapter = charactersAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            addItemDecoration(simpleItemDecorator)
        }
    }

    private fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.characteItemsLiveData.observe(viewLifecycleOwner, Observer {
            charactersAdapter.updateData(it)

        })
        mainViewModel.getCharactersByHouseName(houseName)

    }


}
