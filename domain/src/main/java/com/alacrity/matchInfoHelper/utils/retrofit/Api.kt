package com.alacrity.matchInfoHelper.utils.retrofit

import com.alacrity.matchInfoHelper.entity.Example
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("/public/v2/users")
    fun getData(): Call<List<Example>>

}