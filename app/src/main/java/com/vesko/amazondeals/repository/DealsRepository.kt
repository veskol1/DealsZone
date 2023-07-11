package com.vesko.amazondeals.repository

import com.vesko.amazondeals.api.ApiService
import com.vesko.amazondeals.model.Deal
import javax.inject.Inject

class DealsRepository @Inject constructor(private val api: ApiService) {

    suspend fun getData(): ArrayList<Deal>? {
        return try {
            val response = api.getDeals()
            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}