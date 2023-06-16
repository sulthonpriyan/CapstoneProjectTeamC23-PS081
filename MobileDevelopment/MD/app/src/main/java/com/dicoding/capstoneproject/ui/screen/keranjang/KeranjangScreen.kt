package com.dicoding.capstoneproject.ui.screen.keranjang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun KeranjangScreen(
    modifier: Modifier = Modifier
){
    KeranjangContent()
}

@Composable
fun KeranjangContent(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Keranjang")
    }

}

