package org.fuzzyrobot.kpresent

import rx.Scheduler
import rx.android.plugins.RxAndroidSchedulersHook

class TestSchedulerHook(val scheduler: Scheduler) : RxAndroidSchedulersHook() {
    override fun getMainThreadScheduler(): Scheduler? {
        return scheduler
    }
}