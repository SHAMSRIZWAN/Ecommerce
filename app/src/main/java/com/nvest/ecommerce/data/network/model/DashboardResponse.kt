package com.nvest.ecommerce.data.network.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class DashboardResponseItem(
    @Json(name = "category")
    val category: String? = null,
    @Json(name = "description")
    val description: String? = null,
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "image")
    val image: String? = null,
    @Json(name = "price")
    val price: Double? = null,
    @Json(name = "rating")
    val rating: Rating? = null,
    @Json(name = "title")
    val title: String? = null
) {
    @JsonClass(generateAdapter = true)
    data class Rating(
        @Json(name = "count")
        val count: Int? = null,
        @Json(name = "rate")
        val rate: Double? = null
    )
}