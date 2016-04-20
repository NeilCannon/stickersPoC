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
import org.fuzzyrobot.kpresent.presenter.HeightAdjustingDelegatingPresenter
import org.fuzzyrobot.kpresent.presenter.HeightAdjustingPresenter
import org.fuzzyrobot.kpresent.presenter.StickersPagerPresenter
import org.fuzzyrobot.kpresent.rx.StickerClickSubject
import org.fuzzyrobot.kpresent.rx.StickerService
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onFocusChange
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import javax.inject.Inject

class CommentingFragment : RxFragment() {

    enum class State : Parcelable {
        CLOSED,
        TEXT_MEDIA {
            override fun onKeyboardClosed(): State {
                return State.CLOSED
            }
        },
        STICKERS;

        open fun onKeyboardClosed(): State = this

        override fun writeToParcel(dest: Parcel?, flags: Int) {
            dest?.writeInt(ordinal)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object {
            @JvmField final val CREATOR: Parcelable.Creator<State> = object : Parcelable.Creator<State> {
                override fun createFromParcel(source: Parcel): State {
                    return State.values()[source.readInt()]
                }

                override fun newArray(size: Int): Array<State?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }

    @Inject lateinit var stickerClickSubject: StickerClickSubject
    @Inject lateinit var stickerPurchasesSubject: StickerService

    var mediaBar: InflatingPresenter? = null
    var stickers: DecoratedPresenter? = null

    var stateEvents = StateEvents<State>(State.CLOSED)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // 2
//        Observable.combineLatest(stickerClickSubject.get(), stickerPurchasesSubject.asObservable()) {
//            t1, t2 ->
//            StickerUse(t1, t2)
//        }.bindToLifecycle(this@CommentingFragment).subscribe { println(it.isUsable()) }

    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as MainActivity).activityComponent.inject(this)

        // 1
        stickerClickSubject.get().bindToLifecycle(this@CommentingFragment).subscribe {
//            if (!stickerPurchasesSubject.hasValue()) {
                val sticker = it
                val progressDialog = indeterminateProgressDialog("waiting for sticker purchase info", null, null)
                progressDialog.show()
                stickerPurchasesSubject.asObservable().subscribe {
                    progressDialog.dismiss()
                    val stickerUse = StickerUse(sticker, it)
                    println(stickerUse.isUsable())
                }
//            } else {
//                val stickerUse = StickerUse(it, stickerPurchasesSubject.value)
//                println(stickerUse.isUsable())
//            }
        }

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        stateEvents.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        stateEvents.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        println("onCreateView")
        return inflater?.inflate(R.layout.fragment_commenting, container, false)
    }

    private var keyboardOpenCloseListener: KeyboardOpenCloseListener? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mediaBar = InflatingPresenter(activity, R.id.media_bar_container, R.layout.fragment_media_bar)
//        stickers = HeightAdjustingDelegatingPresenter({ getStickersHeight() }, find(R.id.stickers_container), R.layout.fragment_stickers_pager) { StickersPagerPresenter(it, fragmentManager) }
        val stickersContainer = find<ViewGroup>(R.id.stickers_container)
        stickers = HeightAdjustingPresenter({ getStickersHeight() }, stickersContainer, R.layout.fragment_stickers_pager)
        stickers?.withDecorator(StickersPagerPresenter(stickersContainer, fragmentManager))

        keyboardOpenCloseListener = KeyboardOpenCloseListener(activity.my_content)
        keyboardOpenCloseListener?.listenForKeyboard { keyboardInfo ->
            if (!keyboardInfo.open) {
                println("keyboard closed")
                stateEvents.moveTo({ it.onKeyboardClosed() })
            }
        }

        stateEvents.whenEnterExit(State.TEXT_MEDIA, mediaBar!!)
        stateEvents.whenExit(State.TEXT_MEDIA) {
            comment_text.clearFocus()
            hideKeyboard()
        }
        stateEvents.whenEnterExit(State.STICKERS, stickers!!)
        stateEvents.whenEnterExit(State.TEXT_MEDIA, AlphaFeature(sticker_mode_icon, 0.6f))

        sticker_mode_icon.onClick {
            keyboardOpenCloseListener?.deGlitchKeyboardSlideDown()
            stateEvents.moveTo(State.STICKERS)
        }

        comment_text.onFocusChange { view, hasFocus ->
            if (hasFocus) {
                stateEvents.moveTo(State.TEXT_MEDIA)
            }
        }

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



