package com.techsensei.domain.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var id: Int? = null,
    var profileImage: String? = null,
    var profileImageTest: Int = -1,
    val exists: Boolean = false
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeValue(id)
        parcel.writeString(profileImage)
        parcel.writeInt(profileImageTest)
        parcel.writeByte(if (exists) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
