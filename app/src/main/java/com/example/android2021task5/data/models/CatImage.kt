package com.example.android2021task5.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiData(
    @Json(name = "results") val results: List<CatImage>
)

@JsonClass(generateAdapter = true)
data class CatImage(
    @Json(name = "id") val id: String,
    @Json(name = "url") val url: String?
)
