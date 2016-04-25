package org.fuzzyrobot.kpresent.fragment


import android.os.Bundle
import android.support.v4.app.hackView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.pager_page.view.*
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.mock
import org.fuzzyrobot.kpresent.model.StickerPack
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito.*


class StickerPackPageFragmentTest {

    val sp1 = StickerPack("id1", "icon1", true, 8, "0.99", arrayOf())

    @Test fun testPresenter() {

        val v = mock<StickerPackView>()
        val p = StickerPackPresenter(sp1, v)

        p.present()
        verify(v).updateMessage("Premium - 8 stickers - £0.99")
    }

    @Test fun testFragmentView() {
        val args = mock<Bundle>()
        com.github.vmironov.jetpack.arguments.bundler = { args }
        val v = StickerPackPageFragment.newInstance(sp1)
        val p = mock<StickerPackPresenter>()
        v.presenter.newInstance = { p }
        verify(args).putParcelable("stickerPack", sp1)
//        given(args.getParcelable<StickerPack>(ARG)).willReturn(sp1)

        val inflater = mock<LayoutInflater>()
        val container = mock<ViewGroup>()

        v.onCreateView(inflater, container, null)

        verify(inflater).inflate(R.layout.pager_page, container, false)
        assertEquals(args, v.arguments)

        val fragView = mock<View>()
        val recyclerView = mock<RecyclerView>()
        given(fragView.recycler).willReturn(recyclerView)
        v.hackView(fragView)
        v.onViewCreated(mock(), mock())

        verify(p).present()

    }

    @Test fun testUpdateMessage() {
        val v = StickerPackPageFragment.newInstance(sp1)
        val fragView = mock<View>()
        val message = mock<TextView>()
        given(fragView.message).willReturn(message)
        v.hackView(fragView)
        v.updateMessage("abc")
        verify(message).text = "abc"
        verify(message).visibility = View.VISIBLE

        reset(message)
        v.updateMessage(null)
        verify(message).text = null
        verify(message).visibility = View.INVISIBLE
    }
}