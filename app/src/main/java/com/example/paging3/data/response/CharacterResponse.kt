package com.example.paging3.data.response

import com.example.paging3.presentation.model.CharacterModel
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("location") val location: LocationResponse,
    @SerializedName("image") val image: String
) {
    fun toPresentation(): CharacterModel {
        return CharacterModel(
            id = id,
            name = name,
            isAlive = status == "Alive",
            species = species,
            location = location.name,
            image = image
        )
    }
}
data class LocationResponse(
    @SerializedName("name") val name: String
)
