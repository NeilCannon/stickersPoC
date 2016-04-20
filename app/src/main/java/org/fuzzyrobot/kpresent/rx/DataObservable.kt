package org.fuzzyrobot.kpresent.rx

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Given a function that gets a value (e.g. by making an API call),
 * lazily call it when asObservable() is subscribed to, and cache the value
 * Use Case variations:
 * 1. eagerly fetch the value before subscribers subscribe:
 *      call fetch()
 * 2. refresh:
 *      set value to null and resubscribe
 *
 */
open class DataObservable<T>(private val get: () -> T) {

    private val observable: Observable<T> = Observable.fromCallable {
        fetch()
    }
    var value: T? = null

    @Synchronized
    fun fetch(): T {
        if (value == null) {
            value = get()
        }
        return value!!
    }

    fun asObservable(): Observable<T> = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

}