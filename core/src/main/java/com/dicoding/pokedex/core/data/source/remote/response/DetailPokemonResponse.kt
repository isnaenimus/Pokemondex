package com.dicoding.pokedex.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailPokemonResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("base_experience")
    val base_experience: Int,

    @field:SerializedName("height")
    val height: Int,

    @field:SerializedName("weight")
    val weight: Int,

    @field:SerializedName("order")
    val order: Int,

    @field:SerializedName("sprites")
    val sprites: SpritesResponse
)

data class SpritesResponse(
    @field:SerializedName("front_default")
    val front_default: String,

    @field:SerializedName("front_shiny")
    val front_shiny: String,

    @field:SerializedName("back_default")
    val back_default: String,

    @field:SerializedName("back_shiny")
    val back_shiny: String
)