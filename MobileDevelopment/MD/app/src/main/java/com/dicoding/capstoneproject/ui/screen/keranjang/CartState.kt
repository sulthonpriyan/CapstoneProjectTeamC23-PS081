package com.dicoding.capstoneproject.ui.screen.keranjang

import com.dicoding.capstoneproject.model.OrderProduct

data class CartState(
    val orderProduct: List<OrderProduct>,
    val totalRequiredPoint: Int
)