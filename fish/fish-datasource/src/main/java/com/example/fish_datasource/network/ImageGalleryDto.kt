package com.example.fish_datasource.network

import com.example.fish_domain.SpeciesIllustrationPhoto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageGalleryDto(
    @SerialName("src")
    val src: String,
    @SerialName("alt")
    val alt: String,
    @SerialName("title")
    val title: String
)

fun ImageGalleryDto.toImageGallery(): SpeciesIllustrationPhoto {
    return SpeciesIllustrationPhoto(
        src = src,
        alt = alt,
        title = title
    )
}