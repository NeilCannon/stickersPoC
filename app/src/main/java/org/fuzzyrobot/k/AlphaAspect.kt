package org.fuzzyrobot.k

import android.widget.ImageView


class AlphaAspect(val view: ImageView, val alpha: Float) : Aspect {

    override fun add() {
        view.alpha = alpha
    }

    override fun remove() {
        view.alpha = 1.0f
    }
}