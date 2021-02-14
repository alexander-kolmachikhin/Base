package wsr.base.domain

import retrofit2.Call
import retrofit2.http.GET

interface TestApi {

    @GET("/latest.js")
    fun getLatest(): Call<TestResponse>

}