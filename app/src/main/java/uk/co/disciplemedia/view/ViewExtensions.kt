package uk.co.disciplemedia.view

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.ViewAnimator
import kotlinx.android.synthetic.main.pager_page.view.*


fun View.hideKeyboard() {
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(windowToken, 0);
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

fun View.invisible(invisible: Boolean) {
    visibility = if (invisible) View.INVISIBLE else View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.animate(animResId: Int) {
    startAnimation(AnimationUtils.loadAnimation(context, animResId))
}

//fun View.shake() {
//    animate(R.anim.shake)
//}

@ColorInt fun View.color(@ColorRes id: Int) = resources.getColor(id)

fun ViewAnimator.show(e: Enum<*>) {
    displayedChild = e.ordinal
}

fun TextView.setTextAndVisibility(text: String?) {
    visible(text != null)
    this.text = text
}

fun View.layoutInflater(): LayoutInflater = LayoutInflater.from(context)

fun <T:View> ViewGroup.inflateInto(layoutId: Int): T = layoutInflater().inflate(layoutId, this) as T

