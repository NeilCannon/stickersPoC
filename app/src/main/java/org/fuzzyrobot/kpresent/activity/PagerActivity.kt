package org.fuzzyrobot.kpresent.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_pager.*
import kotlinx.android.synthetic.main.pager_tab.view.*
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.StickersTestData
import org.fuzzyrobot.kpresent.adapter.StickerPackPagerAdapter
import org.fuzzyrobot.kpresent.hideKeyboard
import org.fuzzyrobot.kpresent.load
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onFocusChange

class PagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        //        val thing1 = Thing("123XYZ", "http://www.jrtstudio.com/sites/default/files/ico_android.png", true, 13, "0.79")
        //        val thing2 = Thing("456ABC", "https://cdn0.iconfinder.com/data/icons/picons-social/57/54-slack-128.png", false, 16, "1.29")
        //        val things = arrayListOf(thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2, thing1, thing2)

        pager.adapter = StickerPackPagerAdapter(supportFragmentManager, StickersTestData.things)

        sliding_tabs.setupWithViewPager(pager)
        sliding_tabs.removeAllTabs()
        for (thing in StickersTestData.things) {
            val tabView = layoutInflater.inflate(R.layout.pager_tab, null)
            load(tabView.tab_icon, thing.icon)
            tabView.premium_indicator.visibility = if (thing.premium) View.VISIBLE else View.INVISIBLE
            sliding_tabs.addTab(sliding_tabs.newTab().setCustomView(tabView))
        }

        comment_text_x.onFocusChange { view, b ->
            val keyb = b && view.isEnabled
            mode_switcher.displayedChild = if (keyb) 0 else 1
        }
        sticker_mode_icon_x.onClick {
            mode_switcher.hideKeyboard()
            mode_switcher.postDelayed({ mode_switcher.displayedChild = 1 }, 200)

        }
        comment_text_x.onClick {
            mode_switcher.displayedChild = 0
        }
    }
}

