package com.dicoding.capstoneproject

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.capstoneproject.R
import com.dicoding.capstoneproject.ui.navigation.NavigationItem
import com.dicoding.capstoneproject.ui.navigation.Screen
import com.dicoding.capstoneproject.ui.screen.beranda.BerandaScreen
import com.dicoding.capstoneproject.ui.screen.detail.DetailScreen
import com.dicoding.capstoneproject.ui.screen.keranjang.CartScreen
import com.dicoding.capstoneproject.ui.screen.keranjang.KeranjangScreen
import com.dicoding.capstoneproject.ui.screen.login.LoginPage
import com.dicoding.capstoneproject.ui.screen.pencarian.PencarianScreen
import com.dicoding.capstoneproject.ui.screen.pengaturan.PengaturanScreen
import com.dicoding.capstoneproject.ui.screen.penjualan.PenjualanScreen
import com.dicoding.capstoneproject.ui.theme.CapstoneProjectTheme

@Composable
fun CapstoneProjectApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailProduct.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Beranda.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Beranda.route){
                BerandaScreen()
            }
            composable(Screen.Penjualan.route){
                PenjualanScreen(
                    navigateToDetail = { productId ->
                        navController.navigate(Screen.DetailProduct.createRoute(productId))
                    }
                )
            }
            composable(Screen.Keranjang.route){
                val context = LocalContext.current
                CartScreen(
                    onOrderButtonClicked = { message ->
                        shareOrder(context, message)
                    }
                )
            }
            composable(
                route = Screen.DetailProduct.route,
                arguments = listOf(navArgument("productId") { type = NavType.LongType }),
            ) {
                val id = it.arguments?.getLong("productId") ?: -1L
                DetailScreen(
                    productId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Keranjang.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
//            composable(Screen.Login.route) {
//                LoginPage(navController = navController)
//            }
        }
    }
}

private fun shareOrder(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.dicoding_reward))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.dicoding_reward)
        )
    )
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.navigation_item_beranda),
                icon = Icons.Default.Home,
                screen = Screen.Beranda
            ),
            NavigationItem(
                title = "Cari Produk",
                icon = Icons.Default.Search,
                screen = Screen.Penjualan
            ),
//            NavigationItem(
//                title = stringResource(R.string.navigation_item_pencarian),
//                icon = Icons.Default.Search,
//                screen = Screen.Pencarian
//            ),
            NavigationItem(
                title = stringResource(R.string.navigation_item_keranjang),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Keranjang
            ),
//            NavigationItem(
//                title = stringResource(R.string.navigation_item_pengaturan),
//                icon = Icons.Default.Settings,
//                screen = Screen.Pengaturan
//            ),
        )
        BottomNavigation {

            navigationItems.map {item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = {Text(item.title)},
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CapstoneProjectAppPreview(){
    CapstoneProjectTheme {
        CapstoneProjectApp()
    }
}

