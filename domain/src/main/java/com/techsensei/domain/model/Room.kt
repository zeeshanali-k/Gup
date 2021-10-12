package com.techsensei.domain.model

import android.os.Parcel
import android.os.Parcelable

//The dto will have chats list not single chat
data class Room(
    val id: Int,
    val user: User,
    val lastMessage: Chat
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.readParcelable(Chat::class.java.classLoader)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(lastMessage, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Room> {
        override fun createFromParcel(parcel: Parcel): Room {
            return Room(parcel)
        }

        override fun newArray(size: Int): Array<Room?> {
            return arrayOfNulls(size)
        }
    }
}
