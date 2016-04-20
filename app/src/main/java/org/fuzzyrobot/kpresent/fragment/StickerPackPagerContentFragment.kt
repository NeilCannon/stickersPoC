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
import org.fuzzyrobot.kpresent.model.StickerPack

class StickerPackPagerContentFragment : Fragment() {

    companion object {
        fun newInstance(stickerPack: StickerPack): StickerPackPagerContentFragment = StickerPackPagerContentFragment().apply { arguments = Bundle().apply { putParcelable("thing", stickerPack) } }
    }

    private fun stickerPack(): StickerPack = arguments?.getParcelable("thing")!!

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.pager_page, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        view?.message?.visibility = if (stickerPack().premium) View.VISIBLE else View.INVISIBLE
        view?.message?.text = "Premium - ${stickerPack().count} stickers - £${stickerPack().price}"

        val recycler = view?.recycler
        recycler?.layoutManager = GridLayoutManager(view?.context, 4)
        recycler?.adapter = StickerRecyclerAdapter((activity as MainActivity).activityComponent, stickerPack())
    }
}