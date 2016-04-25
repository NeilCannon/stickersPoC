package org.fuzzyrobot.kpresent.rx.lifecycle

import android.support.annotation.CheckResult
import com.trello.rxlifecycle.OutsideLifecycleException
import com.trello.rxlifecycle.RxLifecycle
import org.fuzzyrobot.k.Aspect
import org.fuzzyrobot.k.DecoratedAspect
import rx.Observable
import rx.functions.Func1
import rx.subjects.BehaviorSubject

enum class AspectEvent { ADD, REMOVE }

open class AspectLifecycleProvider {

    protected val lifecycleSubject = BehaviorSubject.create<AspectEvent>()

    /**
     * @return a sequence of [ScreenFeature] lifecycle events
     */
    @CheckResult
    fun lifecycle(): Observable<AspectEvent> {
        return lifecycleSubject.asObservable()
    }

    /**
     * Binds a source until a specific [AspectEvent] occurs.
     *
     *
     * Intended for use with [Observable.compose]

     * @param event the [AspectEvent] that triggers unsubscription
     * *
     * @return a reusable [rx.Observable.Transformer] which unsubscribes when the event triggers.
     */
    @CheckResult
    fun <T> bindUntilEvent(event: AspectEvent): Observable.Transformer<T, T> {
        return RxLifecycle.bindUntilEvent<T, AspectEvent>(lifecycleSubject, event)
    }

    /**
     * Binds a source until the next reasonable [AspectEvent] occurs.
     *
     *
     * Intended for use with [Observable.compose]

     * @return a reusable [rx.Observable.Transformer] which unsubscribes at the correct time.
     */

}

// Figures out which corresponding next lifecycle event in which to unsubscribe, for Fragments
private val SCREEN_FEATURE_LIFECYCLE = Func1<AspectEvent, AspectEvent> { lastEvent ->
    when (lastEvent) {
        AspectEvent.ADD -> AspectEvent.REMOVE
        AspectEvent.REMOVE -> throw OutsideLifecycleException("Cannot bind to Fragment lifecycle when outside of it.")
        else -> throw UnsupportedOperationException("Binding to $lastEvent not yet implemented")
    }
}

public fun <T> rx.Observable<T>.bindToLifecycle(lp: AspectLifecycleProvider): rx.Observable<T> {
//    return this.compose(lp.bindToLifecycle())
    return this.compose<T>(RxLifecycle.bind(lp.lifecycle()))
}

fun DecoratedAspect.withLifecycle() {
    withDecorator(LifecycleDecoration)
}

object LifecycleDecoration : AspectLifecycleProvider(), Aspect {
    override fun add() {
        println("PresenterEvent.ADD")
        lifecycleSubject.onNext(AspectEvent.ADD)
    }

    override fun remove() {
        println("PresenterEvent.REMOVE")
        lifecycleSubject.onNext(AspectEvent.REMOVE)
    }
}


