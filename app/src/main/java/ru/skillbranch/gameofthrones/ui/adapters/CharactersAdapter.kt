package ru.skillbranch.gameofthrones.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.character_item.view.*
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.R
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem

class CharactersAdapter(private val listener: (CharacterItem) -> Unit) :
    RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    var items: List<CharacterItem> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemId(position: Int): Long {
        return items[position].id.toLong()
    }

    fun updateData(data: List<CharacterItem>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition] == data[newItemPosition]

            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = data.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                items[oldItemPosition].id == data[newItemPosition].id

        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder internal constructor(val view: View) :
        RecyclerView.ViewHolder(view) {
        fun bind(characterItem: CharacterItem) {
            val noInfoString = view.context.resources.getString(R.string.info_is_not_available)
            with(view) {
                tv_character_name.text =
                    if (characterItem.name.isEmpty()) noInfoString else characterItem.name
                tv_character_titles.text =
                    if (characterItem.titles.none { it.isNotBlank() })
                        noInfoString
                    else characterItem.titles.joinToString(separator = "•")
                iv_character_icon.setImageResource(
                    AppConfig.getIconByHouseName(characterItem.house)
                )
                setOnClickListener {
                    listener.invoke(characterItem)
                }
            }

        }

    }
}
