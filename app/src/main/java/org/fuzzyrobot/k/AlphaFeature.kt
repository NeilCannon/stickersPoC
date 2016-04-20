package org.fuzzyrobot.k

import android.widget.ImageView


class AlphaFeature(val view: ImageView, val alpha: Float) : Presenter {

    override fun add() {
        view.alpha = alpha
    }

    override fun remove() {
        view.alpha = 1.0f
    }
}