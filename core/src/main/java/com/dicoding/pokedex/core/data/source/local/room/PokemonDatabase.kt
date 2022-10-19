package com.dicoding.pokedex.core.data.source.local.room

import androidx.room.*
import com.dicoding.pokedex.core.data.source.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class], version = 1, exportSchema = false)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}