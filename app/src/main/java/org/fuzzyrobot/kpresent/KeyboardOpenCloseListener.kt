package org.fuzzyrobot.kpresent

import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.dip
import org.jetbrains.anko.px2dip

class KeyboardOpenCloseListener(val contentView: View) {

    // this is window chrome height
    private var keyboardClosedHeightDiff: Int = 0
    var keyboardHeight: Int = contentView.dip(320) // a guess for first-time

    var maxHeight = 0



    fun listenForKeyboard(l: (keyboardInfo: KeyboardInfo) -> Unit) {
        val debounced = DebouncingListener(l)
        contentView?.getViewTreeObserver()?.addOnGlobalLayoutListener({
//            dump(contentView)
            val heightDiff = contentView!!.getRootView()!!.getHeight() - contentView!!.getHeight();
            //println("$heightDiff : ${contentView!!.getRootView()!!.getHeight()} : ${contentView!!.getHeight()}")
            // if more than 150 dip, its probably a keyboard...
            //println("listenForKeyboard : " + contentView!!.getHeight())
            if (contentView.px2dip(heightDiff) > 150) {
                keyboardHeight = heightDiff - keyboardClosedHeightDiff
                debounced.dispatch(KeyboardInfo(true, keyboardHeight))
            } else {
                maxHeight = getDecorParent().height
                keyboardClosedHeightDiff = heightDiff
                debounced.dispatch(KeyboardInfo(false, keyboardHeight))
            }

        });

    }

    /**
     * The activity has been resized by the soft keyboard. Now we want to show the stickers fragment and hide the keyboard.
     * If we show the fragment before the kb has animated down, we will see a glitch of the stickers shown above the keyboard
     * instead of behind it. So we immediately resize the view to the size it was before the keyboard shrank it - the stickers
     * appear to be waiting behind the keyboard as it slides down. After the keyboard animation has finished we restore the view
     * height to MATCH_PARENT (as it was originally)
     */
    public  fun deGlitchKeyboardSlideDown() {
        if (maxHeight > 0) {
            getDecorParent().layoutParams.height = maxHeight
            contentView?.postDelayed({ getDecorParent().layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT }, 10)
        }
    }

    private fun getDecorParent() = (contentView.getRootView().findViewById(R.id.decor_content_parent).parent as View)

}


fun dump(view: View) {
    println("root: ${view.rootView}")
    dumpv(view)
    var p = view.parent as View
    dumpv(p)
    p = p.parent as View
    dumpv(p)
    p = p.parent as View
    dumpv(p)
    p = p.parent as View
    dumpv(p)
    p = p.parent as View
    dumpv(p)
}

private fun dumpv(p: View) {
    println("${p.height} : ${p.layoutParams.height} : $p")
}

class KeyboardInfo(val open: Boolean, val height: Int) {
    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as KeyboardInfo

        if (open != other.open) return false

        return true
    }

    override fun hashCode(): Int{
        var result = open.hashCode()
        return result
    }

    override fun toString(): String{
        return "KeyboardInfo(open=$open, height=$height)"
    }

}

class DebouncingListener<T>(val l: (t: T) -> Unit) {

    var lastState: T? = null

    fun dispatch(t: T) {
//        println("$lastState -> $t")
        if (lastState == null || t != lastState) {
            l(t)
            lastState = t
        }
    }
}