package com.moingay.cricketsuper.data.model.response
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SportStoriesResponse (
    val data: List<Datum>? = null,
    val meta: Meta? = null
)

@Serializable
data class Datum (
    val id: Long? = null,
    val attributes: DatumAttributes? = null
)

@Serializable
data class DatumAttributes (
    val title: String? = null,
    val slug: String? = null,

    @SerializedName("content_sort")
    val contentSort: String? = null,

    @SerializedName("content_full")
    val contentFull: String? = null,

    val status: Boolean? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val publishedAt: String? = null,
    val seo: String? = null,

    @SerializedName("post_media")
    val postMedia: PostMedia? = null,

    @SerializedName("Category")
    val category: Category? = null
)

@Serializable
data class Category (
    val id: Long? = null
)

@Serializable
data class PostMedia (
    val data: Data? = null
)

@Serializable
data class Data (
    val id: Long? = null,
    val attributes: DataAttributes? = null
)

@Serializable
data class DataAttributes (
    val name: String? = null,
    val alternativeText: String? = null,
    val caption: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val formats: Formats? = null,
    val hash: String? = null,
    val ext: String? = null,
    val mime: String? = null,
    val size: Double? = null,
    val url: String? = null,
    val previewUrl: String? = null,
    val provider: String? = null,

    @SerializedName("provider_metadata")
    val providerMetadata: String? = null,

    val createdAt: String? = null,
    val updatedAt: String? = null
)

@Serializable
data class Formats (
    val thumbnail: Large? = null,
    val small: Large? = null,
    val medium: Large? = null,
    val large: Large? = null
)

@Serializable
data class Large (
    val name: String? = null,
    val hash: String? = null,
    val ext: String? = null,
    val mime: String? = null,
    val path: String? = null,
    val width: Long? = null,
    val height: Long? = null,
    val size: Double? = null,
    val url: String? = null
)

@Serializable
data class Meta (
    val pagination: Pagination? = null
)

@Serializable
data class Pagination (
    val page: Long? = null,
    val pageSize: Long? = null,
    val pageCount: Long? = null,
    val total: Long? = null
)
