package com.vesko.amazondeals.viewmodel

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

    private val _searchBarText = MutableStateFlow("")
    val searchBarText = _searchBarText.asStateFlow()

    private val _uiState = MutableStateFlow(
        UiState(
            status = Status.LOADING,
            list = arrayListOf(),
            filteredList = arrayListOf()
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
                        filteredList = data
                    )
                }
            }
        }
    }

    fun onSearchDeal(searchText: String) {
        val filteredLista = uiState.value.list.filter {
            it.title.contains(searchText)
        }
        _uiState.update { state ->
            state.copy(
                filteredList = filteredLista as ArrayList<Deal>
            )
        }
    }

    data class UiState(
        val status: Status,
        val list: ArrayList<Deal>,
        val filteredList: ArrayList<Deal>
    )


    enum class Status {
        ERROR,
        LOADING,
        DONE
    }
}