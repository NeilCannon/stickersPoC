package org.fuzzyrobot.kpresent.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.vmironov.jetpack.arguments.bindArgument
import kotlinx.android.synthetic.main.pager_page.view.*
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.activity.MainActivity
import org.fuzzyrobot.kpresent.adapter.StickerRecyclerAdapter
import org.fuzzyrobot.kpresent.model.StickerInPack
import org.fuzzyrobot.kpresent.model.StickerPack
import uk.co.disciplemedia.testable.TestableFactory
import uk.co.disciplemedia.view.setTextAndVisibility

interface StickerPackView {
    fun updateMessage(text: String?)
}

class StickerPackPageFragment : Fragment(), StickerPackView {

    companion object {
        fun newInstance(stickerPack: StickerPack): StickerPackPageFragment = StickerPackPageFragment().apply {
            this.stickerPack = stickerPack
        }
    }

    val presenter = TestableFactory() { StickerPackPresenter(stickerPack, this) }
    var stickerPack: StickerPack by bindArgument()
    var onClick: ((StickerInPack) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.pager_page, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        presenter.instance.present()
        view?.recycler?.apply {
            layoutManager = GridLayoutManager(view.context, 4)
            adapter = StickerRecyclerAdapter((activity as MainActivity).activityComponent, stickerPack, onClick)
        }
    }

    override fun updateMessage(text: String?) {
        view?.message?.setTextAndVisibility(text)
    }
}

open class StickerPackPresenter(val stickerPack: StickerPack, val stickerPackView: StickerPackView) {

    open fun present() {
        stickerPackView.updateMessage(if (!stickerPack.premium) null else "Premium - ${stickerPack.count} stickers - £${stickerPack.price}")
    }
}