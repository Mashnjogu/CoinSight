package com.project.coinsight.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChartData(
    val timestamp: Long,
    val price: Double
) : Parcelable