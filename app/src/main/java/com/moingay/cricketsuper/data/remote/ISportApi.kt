package com.moingay.cricketsuper.data.remote

import com.moingay.cricketsuper.data.model.response.SportDetailResponse
import com.moingay.cricketsuper.data.model.response.SportStoriesResponse
import com.moingay.cricketsuper.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ISportApi {
    @GET("slugify/slugs/post/{slug}")
    suspend fun getPostBySlug(
        @Path("slug") slug: String,
        @Query("populate") populate: String = Constants.SportAPI.POPULATE
    ): Response<SportDetailResponse>

    @GET("posts")
    suspend fun getSportPost(
        @Query("pagination") page: Int,
        @Query("sort%5B1%5D") typeSort:String = Constants.SportAPI.TYPE_SORT,
        @Query("populate") populate: String = Constants.SportAPI.POPULATE,
    ): Response<SportStoriesResponse>
}