package com.valter.maytheforcebewith_valterfrancisco.utils

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.valter.maytheforcebewith_valterfrancisco.ui.components.SINGLE_LONG_CLICK_INTERVAL
import com.valter.maytheforcebewith_valterfrancisco.ui.components.SingleClickListener

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.getColor(@ColorRes colorRes: Int) = ContextCompat.getColor(context, colorRes)

/**
 *  Extension-function to prevent 'common double tap' issue
 *  when you're pressing button twice very fast and double action is called.
 */
fun View.setSingleClickListener(
        interval: Long = SINGLE_LONG_CLICK_INTERVAL,
        onSingleClick: (View) -> Unit
) {
    val singleClickListener = SingleClickListener(interval) {
        onSingleClick(it)
    }
    setOnClickListener(singleClickListener)
}

fun String.getInitials() = this.split(' ')
            .mapNotNull { it.firstOrNull()?.toString() }
            .reduce { acc, s -> acc + s }
