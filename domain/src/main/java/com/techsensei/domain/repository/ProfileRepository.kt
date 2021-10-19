package com.techsensei.domain.repository

import com.techsensei.domain.model.Resource
import java.io.ByteArrayOutputStream

interface ProfileRepository {

    suspend fun updateProfileImage(byteArrayOutputStream: ByteArrayOutputStream,userId:Int):Resource<Boolean>

}