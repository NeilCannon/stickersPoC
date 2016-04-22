package org.fuzzyrobot.kpresent.fragment

import org.fuzzyrobot.kpresent.mock
import org.fuzzyrobot.kpresent.model.StickerPack
import org.junit.Test
import org.mockito.BDDMockito.*


class StickerPackPagerContentFragmentTest {

    val sp1 = StickerPack("id1", "icon1", true, 8, "0.99", arrayOf())

    @Test fun testPresenter() {

        val v = mock<StickerPackView>()
        val p = StickerPackPresenter(sp1, v)

        p.init()
        verify(v).updateMessage("Premium - 8 stickers - £0.99")
    }

    @Test fun testFragmentView() {
        val v = StickerPackPagerContentFragment.newInstance(sp1)
    }
}