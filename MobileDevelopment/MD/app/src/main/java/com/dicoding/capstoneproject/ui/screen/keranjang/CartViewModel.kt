package com.dicoding.capstoneproject.ui.screen.keranjang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstoneproject.data.Repository
import com.dicoding.capstoneproject.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CartState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CartState>>
        get() = _uiState

    fun getAddedOrderProducts() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedOrderProducts()
                .collect { orderProduct ->
                    val totalRequiredPoint =
                        orderProduct.sumOf { it.product.requiredPoint * it.count }
                    _uiState.value = UiState.Success(CartState(orderProduct, totalRequiredPoint))
                }
        }
    }

    fun updateOrderProduct(productId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateOrderProduct(productId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderProducts()
                    }
                }
        }
    }
}