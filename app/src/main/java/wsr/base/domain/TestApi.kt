package wsr.base.domain

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TestApi {

    @Headers(
        "Accept: application/json",
        "user-agent: WsrApp"
    )
    @GET("/latest.js")
    fun getLatest(): Call<TestResponse>

    @GET("/user/all")
    fun getUsers(): Call<List<User>>

    @GET("/")
    fun get(): Call<String>
}