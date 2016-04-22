package org.fuzzyrobot.kpresent.model

import android.os.Parcel
import android.os.Parcelable

data class StickerInPack(val stickerPack: StickerPack, val sticker: Sticker)

data class Sticker(val url: String) : Parcelable {
    constructor(source: Parcel) : this(source.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(url)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Sticker> = object : Parcelable.Creator<Sticker> {
            override fun createFromParcel(source: Parcel): Sticker {
                return Sticker(source)
            }

            override fun newArray(size: Int): Array<Sticker?> {
                return arrayOfNulls(size)
            }
        }
    }
}

data class StickerPack(val id: String, val icon: String, val premium: Boolean, val count: Int, val price: String, val stickers: Array<Sticker>) : Parcelable {
    constructor(source: Parcel) : this(source.readString(), source.readString(), 1.toByte().equals(source.readByte()), source.readInt(), source.readString(),
            Array(source.readInt()) {
                source.readParcelable<Sticker>(Sticker.javaClass.classLoader)
            })


    fun get(i: Int) = StickerInPack(this, stickers[i])

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(id)
        dest?.writeString(icon)
        dest?.writeByte((if (premium) 1 else 0).toByte())
        dest?.writeInt(count)
        dest?.writeString(price)
        dest?.writeInt(stickers.size)
        stickers.forEach { dest?.writeParcelable(it, 0) }
//        dest?.writeTypedArray(stickers, 0)
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