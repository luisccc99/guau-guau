package com.example.guau_guau.di

import com.example.guau_guau.api.GuauguauApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(GuauguauApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun provideGuauguauApi(retrofit: Retrofit): GuauguauApi =
        retrofit.create(GuauguauApi::class.java)
}