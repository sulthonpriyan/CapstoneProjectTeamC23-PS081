package com.dicoding.capstoneproject.di

import com.dicoding.capstoneproject.data.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}