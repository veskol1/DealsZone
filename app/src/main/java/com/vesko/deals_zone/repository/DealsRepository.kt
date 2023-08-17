package com.vesko.deals_zone.repository

import com.vesko.deals_zone.api.ApiService
import com.vesko.deals_zone.model.Deal
import javax.inject.Inject

class DealsRepository @Inject constructor(private val api: ApiService) {

    suspend fun getData(): ArrayList<Deal>? {
        return try {
            val response = api.getDeals()
            if (response.isSuccessful && response.body() != null) {
                parseList(response.body()!!)
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun parseList(responseList: ArrayList<Deal>): ArrayList<Deal> {
        responseList.forEach {
            it.imageDealSmall = it.imageDeal.replace("_SL1", "_SL0")  //reduces the image quality
        }

        return responseList
    }
}