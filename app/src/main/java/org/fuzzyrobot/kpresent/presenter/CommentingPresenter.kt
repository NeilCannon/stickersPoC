package org.fuzzyrobot.kpresent.presenter

import android.os.Bundle
import org.fuzzyrobot.k.Aspect
import org.fuzzyrobot.k.SavesState
import org.fuzzyrobot.k.StateActions
import org.fuzzyrobot.kpresent.CommentingState
import org.fuzzyrobot.kpresent.CommentingView
import org.fuzzyrobot.kpresent.KeyboardInfo
import org.fuzzyrobot.kpresent.application.BusySpinner
import org.fuzzyrobot.kpresent.model.Sticker
import org.fuzzyrobot.kpresent.model.StickerInPack
import org.fuzzyrobot.kpresent.model.StickerUse
import org.fuzzyrobot.kpresent.rx.StickerService
import javax.inject.Inject

class CommentingPresenter(val commentingView: CommentingView, val inject: (me: CommentingPresenter) -> Unit) : SavesState {
    @Inject lateinit var stickerService: StickerService
    @Inject lateinit var busySpinner: BusySpinner

    var stateEvents = StateActions<CommentingState>(CommentingState.CLOSED)

    fun init() {
        inject(this)

        stateEvents.whenEnterExit(CommentingState.TEXT_MEDIA) {
            commentingView.showMediaBar(it)
        }
        stateEvents.whenExit(CommentingState.TEXT_MEDIA) {
            commentingView.stopEditing()
        }
        stateEvents.whenEnterExit(CommentingState.STICKERS) {
            commentingView.showStickers(it)
        }
        stateEvents.whenEnterExit(CommentingState.TEXT_MEDIA) {
            commentingView.fadeStickersIcon(it)
        }

    }

    override fun loadState(savedInstanceState: Bundle?) {
        stateEvents.loadState(savedInstanceState)
    }

    override fun saveState(outState: Bundle?) {
        stateEvents.saveState(outState)
    }

    fun onStickerClick(stickerInPack: StickerInPack) {
        busySpinner.show()
        stickerService.asObservable().subscribe {
            busySpinner.hide()
            val stickerUse = StickerUse(stickerInPack, it)
            println(stickerUse.isUsable())
        }

    }

    fun onKeyboardOpenClose(keyboardInfo: KeyboardInfo) {
        if (!keyboardInfo.open) {
            stateEvents.moveTo({ it.onKeyboardClosed() })
        }

    }

    fun showStickers() {
        stateEvents.moveTo(CommentingState.STICKERS)
    }

    fun showText() {
        stateEvents.moveTo(CommentingState.TEXT_MEDIA)
    }
}