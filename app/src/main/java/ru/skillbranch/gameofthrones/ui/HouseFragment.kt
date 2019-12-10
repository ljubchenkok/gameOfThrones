package ru.skillbranch.gameofthrones.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_house.*
import ru.skillbranch.gameofthrones.R


class HouseFragment : Fragment() {




    lateinit var houseName:String

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
    }





}
