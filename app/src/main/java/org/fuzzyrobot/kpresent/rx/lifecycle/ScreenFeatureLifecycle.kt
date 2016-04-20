package org.fuzzyrobot.kpresent.rx.lifecycle

import android.support.annotation.CheckResult
import com.trello.rxlifecycle.OutsideLifecycleException
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.functions.Func1
import rx.subjects.BehaviorSubject

enum class PresenterEvent { ADD, REMOVE }

open class PresenterLifecycleProvider {

    protected val lifecycleSubject = BehaviorSubject.create<PresenterEvent>()

    /**
     * @return a sequence of [ScreenFeature] lifecycle events
     */
    @CheckResult
    fun lifecycle(): Observable<PresenterEvent> {
        return lifecycleSubject.asObservable()
    }

    /**
     * Binds a source until a specific [PresenterEvent] occurs.
     *
     *
     * Intended for use with [Observable.compose]

     * @param event the [PresenterEvent] that triggers unsubscription
     * *
     * @return a reusable [rx.Observable.Transformer] which unsubscribes when the event triggers.
     */
    @CheckResult
    fun <T> bindUntilEvent(event: PresenterEvent): Observable.Transformer<T, T> {
        return RxLifecycle.bindUntilEvent<T, PresenterEvent>(lifecycleSubject, event)
    }

    /**
     * Binds a source until the next reasonable [PresenterEvent] occurs.
     *
     *
     * Intended for use with [Observable.compose]

     * @return a reusable [rx.Observable.Transformer] which unsubscribes at the correct time.
     */
//    @CheckResult
//    fun <T> bindToLifecycle(): Observable.Transformer<T, T> {
//        return RxLifecycle.bind<T, T>(lifecycleSubject, SCREEN_FEATURE_LIFECYCLE)
//    }

}

// Figures out which corresponding next lifecycle event in which to unsubscribe, for Fragments
private val SCREEN_FEATURE_LIFECYCLE = Func1<PresenterEvent, PresenterEvent> { lastEvent ->
    when (lastEvent) {
        PresenterEvent.ADD -> PresenterEvent.REMOVE
        PresenterEvent.REMOVE -> throw OutsideLifecycleException("Cannot bind to Fragment lifecycle when outside of it.")
        else -> throw UnsupportedOperationException("Binding to $lastEvent not yet implemented")
    }
}

public fun <T> rx.Observable<T>.bindToLifecycle(lp: PresenterLifecycleProvider): rx.Observable<T> {
//    return this.compose(lp.bindToLifecycle())
    return this.compose<T>(RxLifecycle.bind(lp.lifecycle()))
}

