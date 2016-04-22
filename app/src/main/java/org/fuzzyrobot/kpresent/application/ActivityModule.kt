package org.fuzzyrobot.kpresent.application

import android.app.Activity
import android.app.ProgressDialog
import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import dagger.Module
import dagger.Provides
import org.fuzzyrobot.kpresent.rx.StickerClickSubject
import org.fuzzyrobot.kpresent.rx.StickerService
import org.jetbrains.anko.indeterminateProgressDialog
import java.lang.ref.WeakReference
import javax.inject.Singleton

class BusySpinner(val activity: Activity) {
    var progressDialog:ProgressDialog? = null

    fun show() {
        progressDialog = activity.indeterminateProgressDialog("", null, null)
        progressDialog?.show()

    }

    fun hide() {
        progressDialog?.dismiss()
    }
}


@Module
class ActivityModule(private val activityLifecycleProvider: ActivityLifecycleProvider, private val activity: Activity) {

    constructor(rxAppCompatActivity: RxAppCompatActivity) : this(rxAppCompatActivity, rxAppCompatActivity)

    @Provides
    @Singleton
    fun provideStickerPurchases() = StickerService().asObservable().bindToLifecycle(activityLifecycleProvider)

    @Provides
    @Singleton
    fun provideBusySpinner(): BusySpinner {
        return BusySpinner(activity)
    }


}
