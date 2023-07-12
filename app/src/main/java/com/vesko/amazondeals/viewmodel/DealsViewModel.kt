package com.vesko.amazondeals.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vesko.amazondeals.model.Deal
import com.vesko.amazondeals.repository.DealsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealsViewModel @Inject constructor(private val repo: DealsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(
            status = Status.LOADING,
            list = arrayListOf(),
            filteredList = arrayListOf(),
            searchBarText = ""
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
                        filteredList = arrayListOf()
                    )
                }
            }
        }
    }

    fun onSearchDeal(searchText: String) {
        Log.d("haha","onSearchDeal")
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

    data class UiState(
        val status: Status,
        val list: ArrayList<Deal>,
        val filteredList: ArrayList<Deal>,
        val searchBarText: String
    )


    enum class Status {
        ERROR,
        LOADING,
        DONE
    }
}