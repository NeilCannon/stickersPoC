package org.fuzzyrobot.kpresent

import org.fuzzyrobot.kpresent.model.StickerPack

class StickersTestData {

    companion object {

        val thing1 = StickerPack("123XYZ", "http://www.jrtstudio.com/sites/default/files/ico_android.png", true, 13, "0.79")
        val thing2 = StickerPack("456ABC", "https://cdn0.iconfinder.com/data/icons/picons-social/57/54-slack-128.png", false, 16, "")
        val thing3 = StickerPack("000000", "https://media.giphy.com/media/a74pSGN7wvT7a/giphy.gif", false, 32, "")
        val things = arrayListOf(thing1, thing2, thing3, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2)

    }
}