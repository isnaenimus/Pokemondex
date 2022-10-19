package com.dicoding.pokedex.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "url")
    var url: String,

    @ColumnInfo(name = "imageFrontDefault")
    var imageFrontDefault: String?,

    @ColumnInfo(name = "imageFrontShiny")
    var imageFrontShiny: String?,

    @ColumnInfo(name = "imageBackDefault")
    var imageBackDefault: String?,

    @ColumnInfo(name = "imageBackShiny")
    var imageBackShiny: String?,

    @ColumnInfo(name = "baseExperience")
    var baseExperience: Int?,

    @ColumnInfo(name = "height")
    var height: Int?,

    @ColumnInfo(name = "weight")
    var weight: Int?,

    @ColumnInfo(name = "order")
    var order: Int?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)