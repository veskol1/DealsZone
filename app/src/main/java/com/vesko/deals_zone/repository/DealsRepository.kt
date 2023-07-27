package com.vesko.deals_zone.repository

import android.util.Log
import com.vesko.deals_zone.api.ApiService
import com.vesko.deals_zone.model.Deal
import javax.inject.Inject

class DealsRepository @Inject constructor(private val api: ApiService) {

    suspend fun getData(): ArrayList<Deal>? {
        return try {
            Log.d("haha","inside try")
            val response = api.getDeals()
            if (response.isSuccessful && response.body() != null) {
                Log.d("haha","inside try sucess~!")
                Log.d("haha","response="+response)
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}