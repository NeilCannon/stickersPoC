package org.fuzzyrobot.kpresent

import org.fuzzyrobot.kpresent.rx.DataObservable
import org.junit.Assert.assertEquals
import org.junit.Test
import rx.android.plugins.RxAndroidPlugins
import rx.schedulers.TestScheduler


class DataObservableTest {

    lateinit var testScheduler: TestScheduler
    var fetchCount = 0
    var subscriber1 = 0
    var subscriber2 = 0
    var value = 7
    val d = DataObservable {
        fetchCount++
        value
    }

    init {
        val instance = RxAndroidPlugins.getInstance()
        instance.reset()
        testScheduler = TestScheduler()
        instance.registerSchedulersHook(TestSchedulerHook(testScheduler))
    }

    @Test fun testLazyFetch() {

        d.asObservable().subscribe { subscriber1 += it }
        d.asObservable().subscribe { subscriber2 += it }

        Thread.sleep(20L)
        testScheduler.triggerActions()
        assertEquals(1, fetchCount)
        assertEquals(7, subscriber1)
        assertEquals(7, subscriber2)
    }

    @Test fun preInitValue() {
        d.value = 5

        d.asObservable().subscribe { subscriber1 += it }
        d.asObservable().subscribe { subscriber2 += it }

        Thread.sleep(20L)
        testScheduler.triggerActions()
        assertEquals(0, fetchCount)
        assertEquals(5, subscriber1)
        assertEquals(5, subscriber2)
    }

    @Test fun preTriggerFetch() {
        d.fetch()
        value = 11

        d.asObservable().subscribe { subscriber1 += it }
        d.asObservable().subscribe { subscriber2 += it }

        Thread.sleep(20L)
        testScheduler.triggerActions()
        assertEquals(1, fetchCount)
        assertEquals(7, subscriber1)
        assertEquals(7, subscriber2)
    }
}


