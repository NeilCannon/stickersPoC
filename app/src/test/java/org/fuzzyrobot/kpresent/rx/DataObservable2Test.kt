package org.fuzzyrobot.kpresent.rx

import org.fuzzyrobot.kpresent.TestSchedulerHook
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import rx.android.plugins.RxAndroidPlugins
import rx.schedulers.TestScheduler


class DataObservable2Test {

    lateinit var testScheduler: TestScheduler
    var fetchCount = 0
    var subscriber1 = 0
    var subscriber2 = 0
    var value = 7
    lateinit var d : DataObservable2<Int>

    init {
        println("init")
        val instance = RxAndroidPlugins.getInstance()
        instance.reset()
        testScheduler = TestScheduler()
        instance.registerSchedulersHook(TestSchedulerHook(testScheduler))
    }

    @Before fun setUp() {
        println("setUp")
        fetchCount = 0
        value = 7
        d = DataObservable2 {
            println("fetch $value")
            fetchCount++
            value
        }
    }

    @Test fun testLazyFetch() {
        println("testLazyFetch")
        d.asObservable().subscribe { subscriber1 += it }
        d.asObservable().subscribe { subscriber2 += it }

        Thread.sleep(20L)
        testScheduler.triggerActions()
        assertEquals(1, fetchCount)
        assertEquals(7, subscriber1)
        assertEquals(7, subscriber2)
    }

    @Test fun preTriggerFetch() {
        println("preTriggerFetch")
        d.refresh()
        Thread.sleep(20L)
        value = 11

        Thread.sleep(20L)
        d.asObservable().subscribe { subscriber1 += it }
        d.asObservable().subscribe { subscriber2 += it }

        Thread.sleep(20L)
        testScheduler.triggerActions()
        assertEquals(1, fetchCount)
        assertEquals(7, subscriber1)
        assertEquals(7, subscriber2)
    }

    @Test fun preInitValue() {
        println("preInitValue")
        d.setValue(5)

        d.asObservable().subscribe { println("subscriber1")
            subscriber1 += it }
        d.asObservable().subscribe { subscriber2 += it }

        Thread.sleep(20L)
        testScheduler.triggerActions()
        assertEquals(0, fetchCount)
        assertEquals(5, subscriber1)
        assertEquals(5, subscriber2)
    }

}


