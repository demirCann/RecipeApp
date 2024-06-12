package com.demircandemir.reciper.data.source.network.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demircandemir.reciper.util.Constants.FAVORITES_TABLE
import kotlinx.serialization.Serializable

@Entity(tableName = FAVORITES_TABLE)
@Serializable
data class Result(
    @PrimaryKey
    val id: Int,
    val image: String,
    val imageType: String,
    val title: String
)
