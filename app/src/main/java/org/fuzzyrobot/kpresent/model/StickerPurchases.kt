package org.fuzzyrobot.kpresent.model

data class StickerPurchases(val ids:List<String>) {

}

data class StickerUse(val sticker:Sticker, val stickerPurchases: StickerPurchases) {

    fun isUsable(): Boolean {
        if (!sticker.stickerPack.premium) {
            return true
        }
        if (stickerPurchases.ids.contains(sticker.stickerPack.id)) {
            return true
        }
        return false
    }

}
