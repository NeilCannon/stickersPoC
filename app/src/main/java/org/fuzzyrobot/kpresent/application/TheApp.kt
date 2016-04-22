package org.fuzzyrobot.kpresent.application

import android.app.Application
import com.facebook.common.logging.FLog
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.listener.RequestListener
import com.facebook.imagepipeline.listener.RequestLoggingListener
import com.trello.rxlifecycle.ActivityLifecycleProvider
import com.trello.rxlifecycle.components.RxActivity
import com.trello.rxlifecycle.components.support.RxAppCompatActivity
import java.util.*

class TheApp : Application() {

    companion object {
        //JvmStatic allow access from java code
        @JvmStatic lateinit var graph: ApplicationComponent
        lateinit var app: TheApp
        lateinit var appModule: AppModule

        fun forActivity(activityLifecycleProvider: RxAppCompatActivity): ActivityComponent {
            println("forActivity")
            return DaggerActivityComponent.builder().appModule(appModule).activityModule(ActivityModule(activityLifecycleProvider)).build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        de.akquinet.android.androlog.Log.setDefaultLogLevel(android.util.Log.DEBUG)

        app = this
        appModule = AppModule(this)
        graph = DaggerApplicationComponent.builder().appModule(appModule).build()

        val requestListeners = HashSet<RequestListener>();
        requestListeners.add(RequestLoggingListener());
        val config = ImagePipelineConfig.newBuilder(this)
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(this, config);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }



//    fun forActivity(activity: RxActivity): ActivityComponent {
//        return DaggerActivityComponent.builder().activityModule(ActivityModule(activity)).build()
//    }
}