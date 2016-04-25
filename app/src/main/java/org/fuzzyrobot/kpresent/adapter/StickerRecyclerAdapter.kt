package org.fuzzyrobot.kpresent.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_item.view.*
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.application.ActivityComponent
import org.fuzzyrobot.kpresent.inflate
import org.fuzzyrobot.kpresent.load
import org.fuzzyrobot.kpresent.model.StickerInPack
import org.fuzzyrobot.kpresent.model.StickerPack
import org.jetbrains.anko.onClick

class StickerRecyclerAdapter(val activityComponent: ActivityComponent, val stickerPack: StickerPack, val onClick: ((StickerInPack) -> Unit)?) : RecyclerView.Adapter<StickerRecyclerAdapter.Holder>() {

    init {
        activityComponent.inject(this)
    }

    open class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun display(stickerInPack: StickerInPack, onClick: ((StickerInPack) -> Unit)?) {
            load(itemView?.icon, stickerInPack.sticker.url)
            itemView?.onClick { onClick?.invoke(stickerInPack) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder? {
        return Holder(inflate(R.layout.recycler_item, parent))
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        val stickerInPack = stickerPack.get(position)
        holder?.display(stickerInPack, onClick)
    }

    override fun getItemCount(): Int {
        return stickerPack.count
    }

}