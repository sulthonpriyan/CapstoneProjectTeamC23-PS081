package com.dicoding.capstoneproject.ui.screen.pencarian

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PencarianScreen(
    modifier: Modifier = Modifier
){
    PencarianContent()
}

@Composable
fun PencarianContent(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Pencarian")
    }

}

