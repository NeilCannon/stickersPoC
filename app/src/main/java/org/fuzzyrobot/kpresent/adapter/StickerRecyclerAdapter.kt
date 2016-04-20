package org.fuzzyrobot.kpresent.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_item.view.*
import org.fuzzyrobot.kpresent.R
import org.fuzzyrobot.kpresent.application.ActivityComponent
import org.fuzzyrobot.kpresent.inflate
import org.fuzzyrobot.kpresent.load
import org.fuzzyrobot.kpresent.model.Sticker
import org.fuzzyrobot.kpresent.model.StickerPack
import org.fuzzyrobot.kpresent.rx.StickerClickSubject
import org.jetbrains.anko.onClick
import javax.inject.Inject

class StickerRecyclerAdapter(val activityComponent: ActivityComponent, val stickerPack: StickerPack) : RecyclerView.Adapter<StickerRecyclerAdapter.Holder>() {

    @Inject lateinit var stickerClick : StickerClickSubject

    init {
        activityComponent.inject(this)
    }

    open class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder? {
        return Holder(inflate(R.layout.recycler_item, parent))
    }

    override fun onBindViewHolder(holder: Holder?, position: Int) {
        if (position == 5) {
            load(holder?.itemView?.icon, "https://media.giphy.com/media/a74pSGN7wvT7a/200.webp")
        } else if (position > 0) {
            load(holder?.itemView?.icon, "http://www.jrtstudio.com/sites/default/files/ico_android.png")
        }
        holder?.itemView?.onClick {
            stickerClick.get().onNext(Sticker(stickerPack, position))
        }

    }

    override fun getItemCount(): Int {
        return 64
    }

}