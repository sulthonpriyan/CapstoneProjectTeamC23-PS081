package com.dicoding.capstoneproject.ui.screen.beranda

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.capstoneproject.R
import com.dicoding.capstoneproject.model.dummyCategory
import com.dicoding.capstoneproject.model.dummyRecommendation
import com.dicoding.capstoneproject.ui.components.CategoryItem
import com.dicoding.capstoneproject.ui.components.RecommendationItem

@Composable
fun BerandaScreen(
    modifier: Modifier = Modifier
){
    BerandaContent()
}

@Composable
fun BerandaContent(
    modifier: Modifier = Modifier
){
    Box(modifier = modifier){
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
        ){
            AplicationName()
            Banner()
            CategoryName()
            CategoryRow()
            RecommendationName()
            RecommendationRow()
        }
    }


}

@Composable
fun AplicationName(modifier: Modifier = Modifier){
    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 8.dp)
    ){
        Text(
            text = "NITANI",
            fontSize = 32.sp
        )
    }
}
//
@Composable
fun Banner(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .padding(16.dp)

    ){
        Image(
            painter = painterResource( R.drawable.banner),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(120.dp)
        )
        Column{
            Text(
                text = "Update News Today",
                fontSize = 25.sp,
                color = Color.White,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center,
                modifier = Modifier

            )
            Text(
                text = "Buy Now!"
            )
        }

    }
}
//
@Composable
fun CategoryName(modifier: Modifier = Modifier){
    Text(
        text = "Kategori",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(16.dp)
    )
}
//
@Composable
fun CategoryRow(modifier: Modifier = Modifier) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        items(dummyCategory, key = { it.textCategory }) { category ->
            CategoryItem(category)
        }
    }
}
//
@Composable
fun RecommendationName(modifier: Modifier = Modifier){
    Text(
        text = "Rekomendasi",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .padding(16.dp)
    )
}
//
@Composable
fun RecommendationRow(modifier: Modifier = Modifier){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(16.dp)
    ) {
        items(dummyRecommendation, key = { it.textRecommendation }) { recommendation ->
            RecommendationItem(recommendation)
        }
    }
}

@Preview
@Composable
fun BerandaScreenPreview(){
    BerandaScreen()
}