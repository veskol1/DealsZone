package com.vesko.deals_zone.api

import com.vesko.deals_zone.model.Deal
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun getDeals(): Response<ArrayList<Deal>>
}