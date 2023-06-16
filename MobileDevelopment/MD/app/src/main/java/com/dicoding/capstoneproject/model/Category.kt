package com.dicoding.capstoneproject.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dicoding.capstoneproject.R

data class Category(
    @DrawableRes val imageCategory: Int,
    @StringRes val textCategory: Int
)

val dummyCategory = listOf(
    R.drawable.icon_category_bibit to R.string.category_bibit,
    R.drawable.icon_category_buah to R.string.category_buah,
    R.drawable.icon_category_bahan_olahan to R.string.category_bahan_olahan,
    R.drawable.icon_category_pupuk to R.string.category_pupuk,
).map { Category(it.first, it.second) }