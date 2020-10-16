package com.myhouse.kotlinsamples.network

import retrofit2.Call
import retrofit2.http.GET

interface RickMortyCharacterEndpoints {
    @GET("/api/character")
    fun getCharacters() : Call<Characters>
}