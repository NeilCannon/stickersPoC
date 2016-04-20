package org.fuzzyrobot.kpresent.rx

import rx.lang.kotlin.BehaviourSubject
import rx.subjects.Subject

open class TestableBehaviorSubject<T> {
    private val subject = BehaviourSubject<T>()

    open fun get(): Subject<T, T> = subject
}