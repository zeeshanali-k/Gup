package com.techsensei.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.techsensei.data.network.ProfileClient
import com.techsensei.domain.model.ProfileImage
import com.techsensei.domain.model.Resource
import com.techsensei.domain.repository.ProfileRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileClient: ProfileClient,
    private val context: Context
) : ProfileRepository {

    private val TAG = "ProfileRepositoryImpl"

    override suspend fun updateProfileImage(
        fileUri: String,
        userId: Int
    ): Resource<ProfileImage> {
        return try {

            val byteArrayOutputStream = getStream(fileUri)
            val requestBody = RequestBody.create(
                "image/*".toMediaTypeOrNull(),byteArrayOutputStream!!.toByteArray()
            )
            byteArrayOutputStream?.let {
                it.close()
            }
//            File name is handled on server side
            val part = MultipartBody.Part.createFormData("profile", "file", requestBody)
            Resource.Success(ProfileImage(profileClient.updateProfileImage(part, userId).url!!))
        } catch (e: Exception) {
            Log.d(TAG, "updateProfileImage: ${e.localizedMessage}")
            Log.d(TAG, "updateProfileImage: ${e.cause}")
            Resource.Error("Failed to update profile image")
        }
    }


    private fun getStream(fileUri:String): ByteArrayOutputStream? {
        val fileInputStream: FileInputStream
        val byteArrayOutputStream: ByteArrayOutputStream
        val file: ByteArray
        try {
            byteArrayOutputStream = ByteArrayOutputStream()
            fileInputStream = context
                .contentResolver
                .openInputStream(Uri.parse(fileUri)) as FileInputStream
            file = ByteArray(4096)
            while (fileInputStream.read(file) != -1) {
                byteArrayOutputStream.write(file)
            }
            fileInputStream.close()
            return byteArrayOutputStream
        } catch (e: IOException) {
            Log.d(
                TAG,
                "getStream: " + e.message
            )
        }
        return null
    }

}