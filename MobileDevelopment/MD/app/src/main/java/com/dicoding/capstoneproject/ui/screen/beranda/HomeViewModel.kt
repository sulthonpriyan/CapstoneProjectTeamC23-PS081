package com.dicoding.capstoneproject.ui.screen.beranda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstoneproject.data.Repository
import com.dicoding.capstoneproject.model.OrderProduct
import com.dicoding.capstoneproject.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderProduct>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderProduct>>>
        get() = _uiState

    fun getAllProducts() {
        viewModelScope.launch {
            repository.getAllProducts()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderProducts ->
                    _uiState.value = UiState.Success(orderProducts)
                }
        }
    }
}