package com.wad.tBook

import android.graphics.drawable.InsetDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

fun RecyclerView.getItemDecoration() =
    DividerItemDecoration(this.context, RecyclerView.VERTICAL).apply {
        val divider =
            ContextCompat.getDrawable(this@getItemDecoration.context, R.drawable.divider)!!
        val margin = resources.getDimensionPixelSize(R.dimen.spacing_medium)
        setDrawable(InsetDrawable(divider, margin, 0, margin, 0))
    }

fun Double.toMoneyFormatted(removeSuffix: Boolean = false): String {
    return DecimalFormat("###,###,##0.00").format(this).apply {
        if (removeSuffix) {
            return this.removeSuffix(".00")
        }
    }
}