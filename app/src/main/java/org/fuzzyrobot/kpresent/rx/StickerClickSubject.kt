package org.fuzzyrobot.kpresent.rx

import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import org.fuzzyrobot.kpresent.model.Sticker
import rx.Observable


open class StickerClickSubject(val activityLifecycleProvider: ActivityLifecycleProvider) : TestablePublishSubject<Sticker>() {

    fun asObservable(): Observable<Sticker> = get().bindToLifecycle(activityLifecycleProvider)

}
