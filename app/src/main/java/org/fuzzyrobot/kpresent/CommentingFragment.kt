package org.fuzzyrobot.kpresent

import android.app.Activity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_commenting.*
import org.fuzzyrobot.k.*
import org.fuzzyrobot.kpresent.activity.MainActivity
import org.fuzzyrobot.kpresent.model.StickerUse
import org.fuzzyrobot.kpresent.presenter.CommentingPresenter
import org.fuzzyrobot.kpresent.presenter.HeightAdjustingDelegatingAspect
import org.fuzzyrobot.kpresent.presenter.HeightAdjustingAspect
import org.fuzzyrobot.kpresent.presenter.StickersPagerAspect
import org.fuzzyrobot.kpresent.rx.StickerClickSubject
import org.fuzzyrobot.kpresent.rx.StickerService
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onFocusChange
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import javax.inject.Inject

interface CommentingView {
    fun stopEditing()
    fun showMediaBar(visible:Boolean)
    fun showStickers(visible:Boolean)
    fun fadeStickersIcon(fade: Boolean)
}

class CommentingFragment : RxFragment(), CommentingView {

    private var keyboardOpenCloseListener: KeyboardOpenCloseListener? = null
    var mediaBar: InflatingAspect? = null
    var stickers: HeightAdjustingDelegatingAspect? = null
    var stickerIconFade: Aspect? = null
    lateinit var commentingPresenter: CommentingPresenter

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as MainActivity).activityComponent.inject(this)

//        stickerClickSubject.get().bindToLifecycle(this@CommentingFragment).subscribe {
//            commentingPresenter.onStickerClick(it)
//        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_commenting, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaBar = InflatingAspect(activity, R.id.media_bar_container, R.layout.fragment_media_bar)

        stickers = HeightAdjustingDelegatingAspect({ getStickersHeight() }, find(R.id.stickers_container), R.layout.fragment_stickers_pager) {
            StickersPagerAspect(it, fragmentManager) {
                commentingPresenter.onStickerClick(it)
            }
        }

        stickerIconFade = AlphaFeature(sticker_mode_icon, 0.6f)

        commentingPresenter = CommentingPresenter(this) { (activity as MainActivity).activityComponent.inject(it) }
        commentingPresenter.init()
        keyboardOpenCloseListener = KeyboardOpenCloseListener(activity.my_content)
        keyboardOpenCloseListener?.listenForKeyboard {
            commentingPresenter.onKeyboardOpenClose(it)
        }

        sticker_mode_icon.onClick {
            keyboardOpenCloseListener?.deGlitchKeyboardSlideDown()
            commentingPresenter.showStickers()
        }

        comment_text.onFocusChange { view, hasFocus ->
            if (hasFocus) {
                commentingPresenter.showText()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commentingPresenter.loadState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        commentingPresenter.saveState(outState)
    }

    override fun stopEditing() {
        comment_text.clearFocus()
        hideKeyboard()
    }

    override fun showMediaBar(visible: Boolean) {
        println("showMediaBar($visible)")
        mediaBar?.addRemove(visible)
    }

    override fun showStickers(visible: Boolean) {
        println("showStickers($visible)")
        stickers?.addRemove(visible)
    }

    override fun fadeStickersIcon(fade: Boolean) {
        stickerIconFade?.addRemove(fade)
    }


    private fun getStickersHeight(): Int {
        if (keyboardOpenCloseListener == null) {
            return 0
        }
        return if (keyboardOpenCloseListener!!.keyboardHeight > 0) {
            keyboardOpenCloseListener!!.keyboardHeight + mediaBar!!.getheight()
        } else {
            0
        }
    }

}



