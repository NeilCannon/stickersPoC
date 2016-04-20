package org.fuzzyrobot.kpresent.presenter

import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_stickers_pager.view.*
import kotlinx.android.synthetic.main.pager_tab.view.*
import org.fuzzyrobot.k.InflatingPresenter
import org.fuzzyrobot.k.Presenter
import org.fuzzyrobot.k.ViewPresenter
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.StickersTestData
import org.fuzzyrobot.kpresent.adapter.StickerPackPagerAdapter
import org.fuzzyrobot.kpresent.load

class StickersPagerPresenter(view: View) : ViewPresenter(view) {
    lateinit var fragmentManager: FragmentManager
    constructor(view: View, fragmentManager: FragmentManager) : this(view){
        this.fragmentManager = fragmentManager
        add()
    }
    override fun add() {
        val pager = view?.pager!!
        val sliding_tabs = view?.sliding_tabs!!

        pager.adapter = StickerPackPagerAdapter(fragmentManager, StickersTestData.things)

        sliding_tabs.setupWithViewPager(pager)
        sliding_tabs.removeAllTabs()
        for (thing in StickersTestData.things) {
            val tabView = LayoutInflater.from(pager.context).inflate(R.layout.pager_tab, null)
            load(tabView.tab_icon, thing.icon)
            tabView.premium_indicator.visibility = if (thing.premium) View.VISIBLE else View.INVISIBLE
            sliding_tabs.addTab(sliding_tabs.newTab().setCustomView(tabView))
        }
    }
}

class HeightAdjustingPresenter(val height:()-> Int, container: ViewGroup, layoutId: Int) : InflatingPresenter(container, layoutId) {

    override fun add() {
        super.add()
        if (height() != 0) {
            setContainerHeight(height())
        }
    }

    override fun remove() {
        super.remove()
        setContainerHeight(0)
    }

    private fun setContainerHeight(containerHeight: Int) {
        view?.layoutParams?.height = containerHeight
    }
}

class HeightAdjustingDelegatingPresenter(val height:()-> Int, container: ViewGroup, layoutId: Int, val f: (view: View) -> Presenter) : InflatingPresenter(container, layoutId) {

    override fun add() {
        super.add()
        if (height() != 0) {
            setContainerHeight(height())
        }
        f(view!!).add()
    }

    override fun remove() {
        super.remove()
        setContainerHeight(0)
    }

    private fun setContainerHeight(containerHeight: Int) {
        container.layoutParams?.height = containerHeight
    }
}