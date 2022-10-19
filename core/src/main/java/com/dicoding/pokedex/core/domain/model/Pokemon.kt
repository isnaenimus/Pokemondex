package com.dicoding.pokedex.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    val id: String,
    val name: String,
    val url: String,
    val imageFrontDefault: String?,
    val imageFrontShiny: String?,
    val imageBackDefault: String?,
    val imageBackShiny: String?,
    val baseExperience: Int?,
    val height: Int?,
    val weight: Int?,
    val order: Int?,
    val isFavorite: Boolean
) : Parcelable