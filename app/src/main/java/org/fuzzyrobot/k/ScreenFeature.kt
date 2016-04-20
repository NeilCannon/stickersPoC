package org.fuzzyrobot.k

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.fuzzyrobot.kpresent.rx.lifecycle.PresenterEvent
import org.fuzzyrobot.kpresent.rx.lifecycle.PresenterLifecycleProvider
import java.util.*

interface Presenter {
    fun add()
    fun remove() {}
}

open class DecoratedPresenter(val decorations: MutableList<Presenter> = ArrayList<Presenter>()) : Presenter {

    constructor(decoration: Presenter) : this(arrayListOf(decoration))

    override fun add() {
        decorations.forEach { it.add() }
    }

    override fun remove() {
        decorations.forEach { it.remove() }
    }

    fun withDecorator(decoration: Presenter) {
        decorations.addIfAbsent(decoration)
    }

}

fun DecoratedPresenter.withLifecycle() {
    withDecorator(LifecycleDecoration)
}

fun DecoratedPresenter.withContents(contents:Presenter) {
    withDecorator(contents)
}

fun DecoratedPresenter.withContents(f:()->Presenter) {
    withContents(f())
}

fun <E> MutableCollection<E>.addIfAbsent(element: E) {
    if (!contains(element)) {
        add(element)
    }
}

object LifecycleDecoration : PresenterLifecycleProvider(), Presenter {
    override fun add() {
        println("PresenterEvent.ADD")
        lifecycleSubject.onNext(PresenterEvent.ADD)
    }

    override fun remove() {
        println("PresenterEvent.REMOVE")
        lifecycleSubject.onNext(PresenterEvent.REMOVE)
    }
}

//open class ContainingPresenter : DecoratedPresenter {
//
//    constructor() : super()
//    constructor(contents: Presenter) : super(contents)
//
//}

class FragmentPresenter(val activity: FragmentActivity, val containerId: Int, val f: () -> Fragment) : DecoratedPresenter() {
    override fun add() {
        super.add()
        activity.addFragment(containerId, f())
    }

    override fun remove() {
        super.remove()
        activity.removeFragment(containerId)
    }
}

open class ViewPresenter(var view: View?) : DecoratedPresenter() {
    init {
        withLifecycle()
    }
    constructor(view: View?, contents: Presenter) : this(view) {
        withContents(contents)
    }
    constructor(view: View?, contents:()-> Presenter) : this(view) {
        withContents(contents)
    }
}

fun ViewPresenter.withContents(f:(view:View)->Presenter) {
    withContents(f(this.view!!))
}


open class InflatingPresenter(val container: ViewGroup, val layoutId: Int) : ViewPresenter(null) {

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




