package com.techsensei.data.network

import com.techsensei.data.network.dto.ProfileImageResponse
import com.techsensei.data.utils.QueryConstants
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ProfileClient {

    @Multipart
    @POST("update-profile-image")
    suspend fun updateProfileImage(
        @Part requestBody: MultipartBody.Part,
        @Query(QueryConstants.ID_QUERY_ARG) userId: Int
    ): ProfileImageResponse
}