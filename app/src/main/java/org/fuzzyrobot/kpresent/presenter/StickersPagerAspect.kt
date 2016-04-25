package org.fuzzyrobot.kpresent.presenter

import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_stickers_pager.view.*
import kotlinx.android.synthetic.main.pager_tab.view.*
import org.fuzzyrobot.k.Aspect
import org.fuzzyrobot.k.InflatingAspect
import org.fuzzyrobot.k.ViewAspect
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.StickersTestData
import org.fuzzyrobot.kpresent.adapter.StickerPackPagerAdapter
import org.fuzzyrobot.kpresent.load
import org.fuzzyrobot.kpresent.model.StickerInPack

class StickersPagerAspect(view: View, val onClick: (StickerInPack) -> Unit) : ViewAspect(view) {
    lateinit var fragmentManager: FragmentManager
    constructor(view: View, fragmentManager: FragmentManager, onClick: (StickerInPack) -> Unit) : this(view, onClick){
        println("StickersPagerPresenter.constructor() $view")
        this.fragmentManager = fragmentManager
    }
    override fun add() {
        println("StickersPagerPresenter.add() $view")
        val pager = view?.pager!!
        val sliding_tabs = view?.sliding_tabs!!

        pager.adapter = StickerPackPagerAdapter(fragmentManager, StickersTestData.things, onClick)

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

class HeightAdjustingAspect(val height:()-> Int, container: ViewGroup, layoutId: Int) : InflatingAspect(container, layoutId) {

    override fun add() {
        super.add()
        println("HeightAdjustingPresenter.add() ${container.id} $view")
        if (height() != 0) {
            setContainerHeight(height())
        }
    }

    override fun remove() {
        println("HeightAdjustingPresenter.remove()")
        super.remove()
        setContainerHeight(0)
    }

    private fun setContainerHeight(containerHeight: Int) {
        println("setContainerHeight($containerHeight")
        view?.layoutParams?.height = containerHeight
    }
}

class HeightAdjustingDelegatingAspect(val height:()-> Int, container: ViewGroup, layoutId: Int, val f: (view: View) -> Aspect) : InflatingAspect(container, layoutId) {

    override fun add() {
        super.add()
        println("HeightAdjustingDelegatingPresenter.add()  ${container.id} $view")
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
        println("setContainerHeight($containerHeight")
        container.layoutParams?.height = containerHeight
    }
}