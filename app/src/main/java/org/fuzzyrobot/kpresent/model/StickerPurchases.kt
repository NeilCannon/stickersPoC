package org.fuzzyrobot.kpresent.model

data class StickerPurchases(val ids:List<String>) {

}

data class StickerUse(val stickerInPack:StickerInPack, val stickerPurchases: StickerPurchases) {

    fun isUsable(): Boolean {
        if (!stickerInPack.stickerPack.premium) {
            return true
        }
        if (stickerPurchases.ids.contains(stickerInPack.stickerPack.id)) {
            return true
        }
        return false
    }

}
