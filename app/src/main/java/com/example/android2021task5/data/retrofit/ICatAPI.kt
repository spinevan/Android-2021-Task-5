package com.example.android2021task5.data.retrofit

import com.example.android2021task5.CAT_API_COUNT_ON_PAGE
import com.example.android2021task5.CAT_API_KEY
import com.example.android2021task5.data.models.CatImage
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ICatAPI {

    @Headers("x-api-key: $CAT_API_KEY")
    @GET("/v1/images/search?limit=$CAT_API_COUNT_ON_PAGE")
    suspend fun getListCatImages(@Query("page") page: Int): List<CatImage>
}
