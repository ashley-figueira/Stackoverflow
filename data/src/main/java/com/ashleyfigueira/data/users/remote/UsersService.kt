package com.ashleyfigueira.data.users.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersService {
    @GET("users")
    fun getUsers(@Query("pageSize") pageSize: Int, @Query("order") order: String,
                 @Query("sort") sort: String = "reputation", @Query("site") site: String = "stackoverflow"): Single<UsersResponse>
}