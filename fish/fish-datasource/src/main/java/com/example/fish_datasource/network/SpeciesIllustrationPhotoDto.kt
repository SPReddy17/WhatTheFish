package com.example.fish_datasource.network

import com.example.fish_domain.SpeciesIllustrationPhoto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SpeciesIllustrationPhotoDto(
    @SerialName("src")
    val src: String? = null,
    @SerialName("alt")
    val alt: String? = null,
    @SerialName("title")
    val title: String?= null
)

fun SpeciesIllustrationPhotoDto.toSpeciesIllustration(): SpeciesIllustrationPhoto {
    return SpeciesIllustrationPhoto(
        src = src,
        alt = alt,
        title = title
    )
}
