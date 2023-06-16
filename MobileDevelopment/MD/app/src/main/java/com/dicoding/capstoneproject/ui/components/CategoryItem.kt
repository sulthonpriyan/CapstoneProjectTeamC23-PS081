package com.dicoding.capstoneproject.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.capstoneproject.R
import com.dicoding.capstoneproject.model.Category

@Composable
fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.width(140.dp),
        shape = RoundedCornerShape(8.dp)
    ){
        Column{
            Image(
                painter = painterResource(category.imageCategory),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .background(Color.Green)
                    .width(140.dp)
//                    .clip(RoundedCornerShape(10.dp))

            ){
                Text(
                    text = stringResource(category.textCategory),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
//                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.paddingFromBaseline(top = 16.dp, bottom = 8.dp)
                )
            }

        }
    }

}

@Preview
@Composable
fun CategoryItemPreview(){
    CategoryItem(category = Category(
        R.drawable.icon_category_bibit,
        R.string.category_bibit
    ),
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}