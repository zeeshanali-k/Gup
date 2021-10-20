package com.techsensei.domain.repository

import com.techsensei.domain.model.ProfileImage
import com.techsensei.domain.model.Resource

interface ProfileRepository {

    suspend fun updateProfileImage(fileUri:String,userId:Int):Resource<ProfileImage>

}