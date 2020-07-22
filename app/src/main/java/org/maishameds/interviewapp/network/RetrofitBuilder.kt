package org.maishameds.interviewapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//singleton object
object RetrofitBuilder {
    private val BASE_URL = "https://jsonplaceholder.typicode.com"

    private val client by lazy { buildClient() }
    private val retrofit by lazy { buildRetrofit(client) }

    private fun buildClient(): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS) // connect timeout
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
        return builder.build()
    }

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}