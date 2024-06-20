package com.example.poligonos.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PointModel(
    @SerializedName("x") var x: Double,
    @SerializedName("y") var y: Double
): Parcelable
