package com.dicoding.capstoneproject.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dicoding.capstoneproject.R

data class Recommendation(
    @DrawableRes val imageRecommendation: Int,
    @StringRes val textRecommendation: Int
)

val dummyRecommendation = listOf(
    R.drawable.img_madu to R.string.rekomendasi_madu,
    R.drawable.img_kelapa to R.string.rekomendasi_kelapa,
    R.drawable.img_lemon to R.string.rekomendasi_lemon,
).map { Recommendation(it.first, it.second) }