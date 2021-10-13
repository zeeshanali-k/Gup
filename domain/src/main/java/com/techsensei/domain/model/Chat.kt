package com.techsensei.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Chat(
    val user: User,
    val message: String,
    val messageTime: String? = null,
    val roomId : Int,
    val id:Int = -1
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(user, flags)
        parcel.writeString(message)
        parcel.writeString(messageTime)
        parcel.writeInt(roomId)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chat> {
        override fun createFromParcel(parcel: Parcel): Chat {
            return Chat(parcel)
        }

        override fun newArray(size: Int): Array<Chat?> {
            return arrayOfNulls(size)
        }
    }
}
