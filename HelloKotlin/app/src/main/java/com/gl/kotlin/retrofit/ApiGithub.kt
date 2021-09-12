package com.gl.kotlin.retrofit

import com.gl.kotlin.model.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiGithub {
    @GET("users/{user}/repos")
    suspend fun listReposKt(@Path("user") user: String): List<Repo>
}

