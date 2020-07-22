package org.maishameds.interviewapp.network


import org.maishameds.interviewapp.models.PostModel
import retrofit2.http.*

interface ApiService {

    @GET("/posts")
    suspend fun getPosts(): PostModel

}
