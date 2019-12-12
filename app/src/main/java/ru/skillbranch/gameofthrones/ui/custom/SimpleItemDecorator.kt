package ru.skillbranch.gameofthrones.ui.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.gameofthrones.R

class SimpleItemDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val divider: Drawable = context.resources.getDrawable(R.drawable.divider, context.theme)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val margin = parent.resources.getDimensionPixelSize(R.dimen.divider_margin)
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val bounds = Rect()
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val left =  parent.paddingLeft + margin
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin
            val bottom = top + divider.intrinsicHeight
            divider.setBounds(left, top, right, bottom)
            divider.draw(c)
        }
    }

}