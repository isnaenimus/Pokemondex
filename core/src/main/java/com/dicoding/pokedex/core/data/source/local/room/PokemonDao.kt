package com.dicoding.pokedex.core.data.source.local.room

import androidx.room.*
import com.dicoding.pokedex.core.data.source.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon")
    fun getAllPokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE isFavorite = 1")
    fun getFavoritePokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE id = :pokemonId")
    fun getDetailPokemon(pokemonId: String): Flow<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :pokemonKeyword || '%'")
    fun getSearchPokemon(pokemonKeyword: String): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)

    @Query("UPDATE pokemon SET imageFrontDefault = :imageFrontDefault, " +
            "imageFrontShiny = :imageFrontShiny, " +
            "imageBackDefault = :imageBackDefault, " +
            "imageBackShiny = :imageBackShiny, " +
            "baseExperience = :baseExperience, " +
            "height = :height, " +
            "weight = :weight, " +
            "`order` = :order WHERE id = :pokemonId")
    suspend fun updatePokemonDetail(pokemonId: String,
                                    imageFrontDefault: String?,
                                    imageFrontShiny: String?,
                                    imageBackDefault: String?,
                                    imageBackShiny: String?,
                                    baseExperience: Int?,
                                    height: Int?,
                                    weight: Int?,
                                    order: Int?,)

    @Update
    fun updateFavoritePokemon(pokemon: PokemonEntity)
}