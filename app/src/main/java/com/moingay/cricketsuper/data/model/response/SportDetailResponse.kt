package com.moingay.cricketsuper.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SportDetailResponse (
    val data: Datum? = null,
    val meta: Meta? = null
)