package com.example.aplikasigithubuser.di.remote

import com.example.aplikasigithubuser.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideGson(): Gson = GsonBuilder().create()

fun provideOkHttpClient(): OkHttpClient{
  val interceptor = HttpLoggingInterceptor()
  if (BuildConfig.DEBUG) interceptor.apply {
    level = HttpLoggingInterceptor.Level.HEADERS
    level = HttpLoggingInterceptor.Level.BODY
  }

  val okHttpClientBuilder = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .writeTimeout(25, TimeUnit.SECONDS)
    .addInterceptor(interceptor)

  return okHttpClientBuilder.build()
}

fun provideRetrofit(
  gson: Gson,
  okHttpClient: OkHttpClient
): Retrofit = Retrofit.Builder()
  .baseUrl(BuildConfig.BASE_URL)
  .addConverterFactory(GsonConverterFactory.create(gson))
  .client(okHttpClient)
  .build()

fun provideGithubServices(
  retrofit: Retrofit
): GithubServices = retrofit.create(GithubServices::class.java)