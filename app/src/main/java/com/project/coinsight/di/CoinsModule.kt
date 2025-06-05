package com.project.coinsight.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import com.project.coinsight.BuildConfig
import com.project.coinsight.data.api.CoinAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object CoinsModule {

    @Provides
    fun provideOkhtpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            // Build a new request with headers
            val request = original.newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("x-cg-demo-api-key", BuildConfig.API_KEY)
                .build()
            chain.proceed(request)
        }
        .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(
            "https://api.coingecko.com/api/v3/"
        )
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideCoinsService(retrofit: Retrofit): CoinAPIService =
        retrofit.create(CoinAPIService::class.java)



}


