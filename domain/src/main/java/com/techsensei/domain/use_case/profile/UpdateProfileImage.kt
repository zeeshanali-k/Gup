package com.techsensei.domain.use_case.profile

import com.techsensei.domain.model.ProfileImage
import com.techsensei.domain.model.Resource
import com.techsensei.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateProfileImage(private val profileRepository: ProfileRepository) {

    operator fun invoke(fileUri:String,userId:Int):Flow<Resource<ProfileImage>> = flow {
        emit(Resource.Loading())
        emit(profileRepository.updateProfileImage(fileUri, userId))
    }

}