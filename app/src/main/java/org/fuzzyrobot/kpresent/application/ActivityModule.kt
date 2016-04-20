package org.fuzzyrobot.kpresent.application

import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import dagger.Module
import dagger.Provides
import org.fuzzyrobot.kpresent.rx.StickerClickSubject
import org.fuzzyrobot.kpresent.rx.StickerService
import javax.inject.Singleton

@Module
class ActivityModule(private val activity: ActivityLifecycleProvider) {

    @Provides
    @Singleton
    fun provideStickerClickSubject() = StickerClickSubject(activity)

    @Provides
    @Singleton
    fun provideStickerPurchases() = StickerService().asObservable().bindToLifecycle(activity)

    @Provides
    @Singleton
    fun provideBusySpinner() = StickerService().asObservable().bindToLifecycle(activity)
}

class BusySpinner {
    fun show() {

    }
}