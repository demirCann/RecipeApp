package com.demircandemir.reciper.data.source.network.response

import kotlinx.serialization.Serializable

@Serializable
data class MealResponse(
    val number: Int,
    val offset: Int,
    val results: List<Result>,
    val totalResults: Int
)
