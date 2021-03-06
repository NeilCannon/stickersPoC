package org.fuzzyrobot.kpresent

import android.view.View
import org.fuzzyrobot.kpresent.adapter.StickerRecyclerAdapter
import org.fuzzyrobot.kpresent.adapter.StickerRecyclerAdapter.Holder
import org.fuzzyrobot.kpresent.application.ActivityComponent
import org.fuzzyrobot.kpresent.application.TheApp
import org.fuzzyrobot.kpresent.model.Sticker
import org.fuzzyrobot.kpresent.model.StickerPack
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.BDDMockito.*
import rx.subjects.Subject


class StickerRecyclerAdapterTest {

    @Test fun test() {

        val subject = mock<Subject<Sticker, Sticker>>()

        TheApp.graph = mock()

        val activityComponent = mock<ActivityComponent>()
        val stickerPack = StickerPack("123", "x", true, 3, "£0.99", arrayOf())
        val r = StickerRecyclerAdapter(activityComponent, stickerPack, {})
        assertNotNull(r.onClick)

        val itemView = mock<View>()
        var listener: View.OnClickListener? = null
        given(itemView.setOnClickListener(any())).will {
            listener = (it.arguments[0] as View.OnClickListener)
            return@will null
        }
        val holder = Holder(itemView)
        r.bindViewHolder(holder, -1)
        assertNotNull(listener)
        listener?.onClick(itemView)
//        verify(subject).onNext(Sticker(stickerPack, -1))
    }
}