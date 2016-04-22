package org.fuzzyrobot.kpresent

import org.fuzzyrobot.kpresent.model.Sticker
import org.fuzzyrobot.kpresent.model.StickerPack

class StickersTestData {

    companion object {

        val icon1 = "http://www.jrtstudio.com/sites/default/files/ico_android.png"
        val icon2 = "https://media.giphy.com/media/a74pSGN7wvT7a/200.webp"
        val stickers: Array<Sticker> = Array(64, { Sticker(if (it == 5) icon2 else icon1) })

        val thing1 = StickerPack("123XYZ", "http://www.jrtstudio.com/sites/default/files/ico_android.png", true, 13, "0.79", stickers)
        val thing2 = StickerPack("456ABC", "https://cdn0.iconfinder.com/data/icons/picons-social/57/54-slack-128.png", false, 16, "", stickers)
        val thing3 = StickerPack("000000", "https://media.giphy.com/media/a74pSGN7wvT7a/giphy.gif", false, 32, "", stickers)
        val things = arrayListOf(thing1, thing2, thing3, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2)

    }
}