package com.android.example.booksforyou.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.nytimes.com/svc/books/v3/lists/"
private const val API_KEY = "GWEBk7DlejWKk9svPi9ozjGGRgPJK7zF"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface BooksApiService { //how Retrofit talks to the web server using HTTP requests
    @GET("overview.json?api-key=$API_KEY")
    suspend fun getBooks(): NYTBook
}

//expose Retrofit API-SERVICE
object BooksApi {
    val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }
}
