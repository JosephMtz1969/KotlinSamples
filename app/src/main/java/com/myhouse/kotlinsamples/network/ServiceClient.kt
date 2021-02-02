package com.myhouse.kotlinsamples.network

import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ServiceClient {
    private val serviceApi = ServiceClientBuilder.buildService(ServiceClientEndpoints::class.java)

    fun getCharacters() : Observable<Characters> {
        return serviceApi.getCharacters()
    }
}

interface ServiceClientEndpoints {
    @GET("/api/character")
    fun getCharacters() : Observable<Characters>
}

object ServiceClientBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service: Class<T>) : T{
        return retrofit.create(service)
    }
}
