package org.fuzzyrobot.kpresent.rx

import rx.lang.kotlin.PublishSubject
import rx.subjects.Subject

open class TestablePublishSubject<T> {
    private val subject = PublishSubject<T>()

    open fun get(): Subject<T, T> = subject
}