package org.fuzzyrobot.k

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.fuzzyrobot.kpresent.rx.lifecycle.withLifecycle
import java.util.*

interface Aspect {
    fun add()
    fun remove() {}
    fun addRemove(add: Boolean) { if (add) add() else remove() }
}

interface SavesState {
    fun loadState(savedInstanceState: Bundle?)
    fun saveState(outState: Bundle?)
}

open class DecoratedAspect(val decorations: MutableList<Aspect> = ArrayList<Aspect>()) : Aspect {

    constructor(decoration: Aspect) : this(arrayListOf(decoration))

    override fun add() {
        decorations.forEach { it.add() }
    }

    override fun remove() {
        decorations.forEach { it.remove() }
    }

    fun withDecorator(decoration: Aspect) {
        decorations.addIfAbsent(decoration)
    }

}

fun DecoratedAspect.withContents(contents: Aspect) {
    withDecorator(contents)
}

fun DecoratedAspect.withContents(f: () -> Aspect) {
    withContents(f())
}

fun <E> MutableCollection<E>.addIfAbsent(element: E) {
    if (!contains(element)) {
        add(element)
    }
}

class FragmentAspect(val activity: FragmentActivity, val containerId: Int, val f: () -> Fragment) : DecoratedAspect() {
    override fun add() {
        super.add()
        activity.addFragment(containerId, f())
    }

    override fun remove() {
        super.remove()
        activity.removeFragment(containerId)
    }
}

open class ViewAspect(var view: View?) : DecoratedAspect() {
    init {
        withLifecycle()
    }

    constructor(view: View?, contents: Aspect) : this(view) {
        withContents(contents)
    }

    constructor(view: View?, contents: () -> Aspect) : this(view) {
        withContents(contents)
    }
}


open class InflatingAspect(val container: ViewGroup, val layoutId: Int) : ViewAspect(null) {

    constructor(activity: FragmentActivity, rootId: Int, layoutId: Int)
    : this(activity.findViewById(rootId) as ViewGroup, layoutId)


    override fun add() {
        super.add()
        val inflater = LayoutInflater.from(container.context)
        view = inflater.inflate(layoutId, container, false)
        container.addView(view)
    }

    override fun remove() {
        super.remove()
        container.removeView(view)
    }

    fun getheight() = container.height
}




