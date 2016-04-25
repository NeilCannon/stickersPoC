package org.fuzzyrobot.kpresent.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import org.fuzzyrobot.kpresent.fragment.StickerPackPageFragment
import org.fuzzyrobot.kpresent.model.StickerInPack
import org.fuzzyrobot.kpresent.model.StickerPack

class StickerPackPagerAdapter(fragmentManager: FragmentManager, val stickerPacks: List<StickerPack>, val onClick: (StickerInPack) -> Unit) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment? {
        return StickerPackPageFragment.newInstance(stickerPacks[position])
    }

    override fun getCount(): Int = stickerPacks.size

    /**
     * after an item is instantiated, need to restore its onClick listener
     * (this is for the rotation/from-long-background case when everything has been destroyed, not for regular paging)
     */
    override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
        val item = super.instantiateItem(container, position) as StickerPackPageFragment
        item.onClick = onClick
        return item
    }

}