package com.example.android2021task5.data.retrofit

import com.example.android2021task5.CAT_API_BASE_URL
import com.example.android2021task5.data.models.CatImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CatAPIRetrofitClient {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(CAT_API_BASE_URL)
        .build()

    private val catImagesService = retrofit.create(ICatAPI::class.java)

    suspend fun getCatImages(page: Int): List<CatImage> =
        withContext(Dispatchers.IO) {
            catImagesService.getListCatImages(page)
                .map { result ->
                    CatImage(
                        result.id,
                        result.url
                    )
                }
        }
}
