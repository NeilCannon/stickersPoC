package org.fuzzyrobot.kpresent.application

import android.app.Application
import dagger.Module
import dagger.Provides
import org.fuzzyrobot.kpresent.api.StickerApi
import org.fuzzyrobot.kpresent.model.StickerPurchases
import org.fuzzyrobot.kpresent.rx.StickerService
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

//    @Provides
//    @Singleton
//    fun provideStickerClickSubject() = StickerClickSubject()

    @Provides
    @Singleton
    fun provideStickerPurchasesSubject() = StickerService()

    @Provides
    @Singleton
    fun provideStickerApi() = object : StickerApi {
        override fun listPurchases(): StickerPurchases {
            Thread.sleep(1000L)
            return StickerPurchases(listOf())
        }
    }

}