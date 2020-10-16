package com.myhouse.kotlinsamples.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickMortyCharacterEndpoints {
    @GET("/api/character")
    fun getCharacters(@Query("api_key") key: String) : Call<Characters>
}