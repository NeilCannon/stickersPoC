package org.fuzzyrobot.kpresent.application

import dagger.Component
import org.fuzzyrobot.kpresent.activity.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface ApplicationComponentK {
    fun inject(mainActivity: MainActivity)
}

