package com.andersen.presentation.di.modules

import com.andersen.data.network.ApiInterface
import com.andersen.presentation.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiInterface = retrofit.create(ApiInterface::class.java)

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideLogger() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY.takeIf { BuildConfig.DEBUG } ?: HttpLoggingInterceptor.Level.NONE
    )

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    private companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }
}