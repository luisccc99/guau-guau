package com.guau_guau.guau_guau.di

import com.guau_guau.guau_guau.data.network.GuauguauApi
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(GuauguauApi.BASE_URL)
            .client(
                OkHttpClient.Builder().build()
            )
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
                    )
            )
            .build()

    @Provides
    @Singleton
    fun provideGuauguauApi(retrofit: Retrofit): GuauguauApi =
        retrofit.create(GuauguauApi::class.java)
}