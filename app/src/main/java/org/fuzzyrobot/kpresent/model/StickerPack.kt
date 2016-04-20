package org.fuzzyrobot.kpresent.model

import android.os.Parcel
import android.os.Parcelable

data class Sticker(val stickerPack: StickerPack, val position: Int)

data class StickerPack(val id: String, val icon: String, val premium: Boolean, val count: Int, val price: String) : Parcelable {
    constructor(source: Parcel): this(source.readString(), source.readString(), 1.toByte().equals(source.readByte()), source.readInt(), source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(icon)
        dest?.writeByte((if (premium) 1 else 0).toByte())
        dest?.writeInt(count)
        dest?.writeString(price)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<StickerPack> = object : Parcelable.Creator<StickerPack> {
            override fun createFromParcel(source: Parcel): StickerPack {
                return StickerPack(source)
            }

            override fun newArray(size: Int): Array<StickerPack?> {
                return arrayOfNulls(size)
            }
        }
    }
}