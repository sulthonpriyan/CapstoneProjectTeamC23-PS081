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
import com.dicoding.capstoneproject.model.Recommendation

@Composable
fun RecommendationItem(
    recommendation: Recommendation,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.width(140.dp),
        shape = RoundedCornerShape(8.dp)
    ){
        Column{
            Image(
                painter = painterResource(recommendation.imageRecommendation),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .width(140.dp)
//                    .clip(RoundedCornerShape(10.dp))

            ){
                Text(
                    text = stringResource(recommendation.textRecommendation),
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
fun RecommendationItemPreview(){
    RecommendationItem(recommendation = Recommendation(
        R.drawable.img_kelapa,
        R.string.rekomendasi_kelapa
    ),
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}