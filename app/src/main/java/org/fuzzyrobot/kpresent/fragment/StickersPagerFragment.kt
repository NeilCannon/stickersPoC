package org.fuzzyrobot.kpresent.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_pager.*
import kotlinx.android.synthetic.main.pager_tab.view.*
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.StickersTestData
import org.fuzzyrobot.kpresent.adapter.StickerPackPagerAdapter
import org.fuzzyrobot.kpresent.load

class StickersPagerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_stickers_pager, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

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

