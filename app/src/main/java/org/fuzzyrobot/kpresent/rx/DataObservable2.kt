package org.fuzzyrobot.kpresent.rx

import rx.Observable
import rx.Scheduler
import rx.android.schedulers.AndroidSchedulers
import rx.lang.kotlin.BehaviourSubject
import rx.schedulers.Schedulers
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Given a function that gets a value (e.g. by making an API call),
 * call it when get() is called, and cache the value
 *
 */
open class DataObservable2<T>(var f: (() -> T)? = null) {

    private val lock = ReentrantLock()
    private val subject = BehaviourSubject<T>()
    private var value: T? = null

    fun asObservable(): Observable<T> {
        lock.withLock {
            if (value == null) {
                doWork()
            }
        }
        return subject.observeOn(AndroidSchedulers.mainThread())
    }

    fun refresh() {
        doWork()
    }

    fun setValue(newValue:T) {
        value = newValue
        subject.onNext(value)
    }

    fun hasValue() : Boolean {
        println("${this} : $value")
        return value != null
    }

    private var worker: Scheduler.Worker? = null

    private fun doWork() {
        lock.withLock {
            if (worker != null) {
                return
            }
            worker = Schedulers.io().createWorker()
            worker?.schedule {
                value = f?.invoke()
                subject.onNext(value)
                worker = null
            }
        }
    }

}

