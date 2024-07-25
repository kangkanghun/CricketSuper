package com.moingay.cricketsuper.data.model.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MatchResultRequest (
    val start: String? = null,
    val end: String? = null
)

@Serializable
data class MatchIdRequest(
    @SerializedName("MatchId")
    val matchId: String? = null
)