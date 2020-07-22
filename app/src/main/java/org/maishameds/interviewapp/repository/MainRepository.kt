package org.maishameds.interviewapp.repository

import org.maishameds.interviewapp.models.PostModel
import org.maishameds.interviewapp.network.ApiService
import org.maishameds.interviewapp.network.Resource
import org.maishameds.interviewapp.network.ResponseHandler
import org.maishameds.interviewapp.network.RetrofitBuilder

class MainRepository {
    private var apiService: ApiService = RetrofitBuilder.createService(ApiService::class.java)
    private val responseHandler = ResponseHandler()

    suspend fun getPosts(): Resource<PostModel> {
        return try {
            val response = apiService.getPosts()
            responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}