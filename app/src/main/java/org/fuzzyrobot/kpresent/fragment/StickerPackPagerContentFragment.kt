package org.fuzzyrobot.kpresent.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.pager_page.view.*
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.activity.MainActivity
import org.fuzzyrobot.kpresent.adapter.StickerRecyclerAdapter
import org.fuzzyrobot.kpresent.model.StickerInPack
import org.fuzzyrobot.kpresent.model.StickerPack

interface StickerPackView {
    fun updateMessage(text: String?)
}

class StickerPackPagerContentFragment : Fragment(), StickerPackView {

    var onClick: ((StickerInPack) -> Unit)? = null

    companion object {
        val ARG = "arg"
        fun newInstance(stickerPack: StickerPack): StickerPackPagerContentFragment = StickerPackPagerContentFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG, stickerPack) }
        }
    }

    private fun stickerPack(): StickerPack = arguments?.getParcelable(ARG)!!

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.pager_page, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        StickerPackPresenter(stickerPack(), this).init()

        val recycler = view?.recycler
        recycler?.layoutManager = GridLayoutManager(view?.context, 4)
        recycler?.adapter = StickerRecyclerAdapter((activity as MainActivity).activityComponent, stickerPack(), { onClick?.invoke(it) })
    }

    override fun updateMessage(text: String?) {
        view?.message?.visibility = if (text != null) View.VISIBLE else View.INVISIBLE
        view?.message?.text = text
    }
}

class StickerPackPresenter(val stickerPack: StickerPack, val stickerPackView: StickerPackView) {

    fun init() {
        stickerPackView.updateMessage(if (!stickerPack.premium) null else "Premium - ${stickerPack.count} stickers - £${stickerPack.price}")
    }
}