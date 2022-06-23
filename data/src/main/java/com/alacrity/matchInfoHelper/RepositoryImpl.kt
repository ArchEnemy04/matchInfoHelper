package com.alacrity.matchInfoHelper

import com.alacrity.matchInfoHelper.entity.Example
import com.alacrity.matchInfoHelper.utils.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RepositoryImpl @Inject constructor(
    private val retrofit: Retrofit
) : Repository {


    private lateinit var todoApi: Api

    override suspend fun getData(): List<Example> {
        todoApi = retrofit.create(Api::class.java)
        return suspendCoroutine { continuation ->
            val api = retrofit.create(Api::class.java)
            val call = api.getData()
            call.enqueue(object : Callback<List<Example>> {
                override fun onResponse(
                    call: Call<List<Example>>,
                    response: Response<List<Example>>
                ) {
                    val dataList = response.body()
                    dataList ?: continuation.resumeWithException(NullPointerException())
                        .also { return }
                    continuation.resume(dataList!!)
                }

                override fun onFailure(call: Call<List<Example>>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }

    }
}