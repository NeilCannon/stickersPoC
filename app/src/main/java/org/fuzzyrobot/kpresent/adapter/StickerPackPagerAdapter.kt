package org.fuzzyrobot.kpresent.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup
import org.fuzzyrobot.kpresent.fragment.StickerPackPagerContentFragment
import org.fuzzyrobot.kpresent.model.StickerInPack
import org.fuzzyrobot.kpresent.model.StickerPack

class StickerPackPagerAdapter(fragmentManager: FragmentManager, val stickerPacks: List<StickerPack>, val onClick: (StickerInPack) -> Unit) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment? {
        return StickerPackPagerContentFragment.newInstance(stickerPacks[position])
    }

    override fun getCount(): Int = stickerPacks.size

    override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
        val item = super.instantiateItem(container, position) as StickerPackPagerContentFragment
        item.onClick = onClick
        return item
    }

}