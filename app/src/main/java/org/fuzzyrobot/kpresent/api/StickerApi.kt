package org.fuzzyrobot.kpresent.api

import org.fuzzyrobot.kpresent.model.StickerPurchases

interface StickerApi {
    fun listPurchases(): StickerPurchases
}