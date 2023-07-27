package com.vesko.deals_zone.viewmodel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vesko.deals_zone.model.Deal
import com.vesko.deals_zone.repository.DealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealsViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val dataStore: DataStore<Preferences>,
    private val repo: DealsRepository
) : ViewModel() {

    private val DEALS = stringSetPreferencesKey("deals")

    private val _uiState = MutableStateFlow(
        UiState(
            status = Status.LOADING,
            list = arrayListOf(),
            filteredList = arrayListOf(),
            searchBarText = "",
            favoriteSavedDeals = arrayListOf(),
            dealsByCategory = arrayListOf()
        )
    )

    val uiState = _uiState.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            val data = repo.getData()

            if (data == null) {
                _uiState.update { state ->
                    state.copy(
                        status = Status.ERROR,
                    )
                }
            } else {

                _uiState.update { state ->
                    state.copy(
                        status = Status.DONE,
                        list = data,
                        filteredList = arrayListOf(),
                        favoriteSavedDeals = getFavoriteDealsFromDataStore(dealsList = data, context = context)
                    )
                }
            }
        }
    }
    suspend fun saveToDataStore(context: Context, id: String) {
        val dealsPreferences: Preferences = dataStore.data.first()
        val stringDealsSet = dealsPreferences[DEALS]

        val favoriteDealsSet = stringDealsSet?.toMutableSet()

        favoriteDealsSet?.let {
            if (favoriteDealsSet.contains(id)) {
                favoriteDealsSet.remove(id)
            } else {
                favoriteDealsSet.add(id)
            }

            dataStore.edit { favorites ->
                favorites[DEALS] = favoriteDealsSet
            }
        } ?: run{
            dataStore.edit { favorites ->
                favorites[DEALS] = setOf(id)
            }
        }
    }

    fun updateFavoriteDealsState(dealId: String) {
        val favoriteDealList =
            uiState.value.favoriteSavedDeals.toMutableList() // This only will trigger rerendering
        val allDealsList = uiState.value.list

        if (favoriteDealList.any {
                it.id == dealId
            }) {    //remove
            val dealToRemove = favoriteDealList.find { it.id == dealId }
            favoriteDealList.remove(dealToRemove)
            _uiState.update {
                it.copy(
                    favoriteSavedDeals = favoriteDealList as ArrayList<Deal>
                )
            }
        } else {    //add
            val dealToAdd = allDealsList.find { it.id == dealId }
            favoriteDealList.add(dealToAdd!!)
            _uiState.update {
                it.copy(
                    favoriteSavedDeals = favoriteDealList as ArrayList<Deal>
                )
            }
        }
    }
    private suspend fun getFavoriteDealsFromDataStore(dealsList: ArrayList<Deal>, context: Context): ArrayList<Deal> {
        val dealsPreferences: Preferences = dataStore.data.first()
        val stringDealsSet = dealsPreferences[DEALS]
        val favoriteDealList = arrayListOf<Deal>()

        stringDealsSet?.forEach { savedDealId ->
            val dealToAdd = dealsList.find { deal ->
                deal.id == savedDealId
            }
            dealToAdd?.let {
                favoriteDealList.add(dealToAdd)
            }
        }
        return favoriteDealList
    }

    fun onSearchDeal(searchText: String) {
        if (searchText.isEmpty()) {
            _uiState.update { state ->
                state.copy(
                    filteredList = arrayListOf(),
                    searchBarText = searchText
                )
            }
        } else {
            val filteredNewList = uiState.value.list.filter {
                it.title.contains(searchText, ignoreCase = true)
            }
            _uiState.update { state ->
                state.copy(
                    filteredList = filteredNewList as ArrayList<Deal>,
                    searchBarText = searchText
                )
            }
        }
    }

    fun findDeal(dealId: String): Deal {
        return uiState.value.list.find { it.id == dealId }!!
    }

    fun updateCategoryDeals(category: String) {
        _uiState.update { state ->
            state.copy(
                dealsByCategory = uiState.value.list.filter { it.category == category } as ArrayList
            )
        }
    }

    data class UiState(
        val status: Status,
        val list: ArrayList<Deal>,
        val filteredList: ArrayList<Deal>,
        val searchBarText: String,
        val favoriteSavedDeals: ArrayList<Deal>,
        val dealsByCategory: ArrayList<Deal>,
    )


    enum class Status {
        ERROR,
        LOADING,
        DONE
    }
}