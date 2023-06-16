package com.dicoding.capstoneproject.ui.screen.pengaturan

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PengaturanScreen(
    modifier: Modifier = Modifier
){
    PengaturanContent()
}

@Composable
fun PengaturanContent(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Pengaturan")
    }

}

