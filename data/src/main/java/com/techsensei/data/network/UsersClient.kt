package com.techsensei.data.network

import com.techsensei.data.utils.QueryConstants
import com.techsensei.domain.model.User
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersClient {

    @GET("get-users")
    suspend fun getUsers(@Query(QueryConstants.ID_QUERY_ARG) userId:Int):List<User>

}