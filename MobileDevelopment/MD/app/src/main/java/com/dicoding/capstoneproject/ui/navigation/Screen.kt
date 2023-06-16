package com.dicoding.capstoneproject.ui.navigation

sealed class Screen(val route: String) {
    object Beranda : Screen("beranda")
    object Penjualan : Screen("penjualan")
    object Pencarian : Screen("pencarian")
    object Keranjang : Screen("keranjang")
    object Pengaturan : Screen("pengaturan")
    object Login : Screen("login")
    object DetailProduct : Screen("home/{productId}") {
        fun createRoute(productId: Long) = "home/$productId"
    }
//    object DetailReward : Screen("home/{rewardId}") {
//        fun createRoute(rewardId: Long) = "home/$rewardId"
//    }
}