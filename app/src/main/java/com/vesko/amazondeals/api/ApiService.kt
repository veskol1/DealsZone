package com.vesko.amazondeals.api

import com.vesko.amazondeals.model.Deal
import com.vesko.amazondeals.model.Deals
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun getDeals(): Response<ArrayList<Deal>>
}