package org.fuzzyrobot.kpresent

import android.os.Parcel
import android.os.Parcelable

enum class CommentingState : Parcelable {
    CLOSED,
    TEXT_MEDIA {
        override fun onKeyboardClosed(): CommentingState {
            return CLOSED
        }
    },
    STICKERS;

    open fun onKeyboardClosed(): CommentingState = this

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<CommentingState> = object : Parcelable.Creator<CommentingState> {
            override fun createFromParcel(source: Parcel): CommentingState {
                return values()[source.readInt()]
            }

            override fun newArray(size: Int): Array<CommentingState?> {
                return arrayOfNulls(size)
            }
        }
    }
}