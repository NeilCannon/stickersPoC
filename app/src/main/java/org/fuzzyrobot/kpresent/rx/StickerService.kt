package org.fuzzyrobot.kpresent.rx

import org.fuzzyrobot.kpresent.api.StickerApi
import org.fuzzyrobot.kpresent.application.TheApp
import org.fuzzyrobot.kpresent.model.StickerPurchases
import javax.inject.Inject

open class StickerService() : DataObservable2<StickerPurchases>() {

    @Inject lateinit var stickerApi: StickerApi

    init {
        TheApp.graph.inject(this)
        f = { stickerApi.listPurchases() }
    }
}
