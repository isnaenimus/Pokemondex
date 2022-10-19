package com.dicoding.pokedex.core.data.source.local

import com.dicoding.pokedex.core.data.source.local.entity.PokemonEntity
import com.dicoding.pokedex.core.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val pokemonDao: PokemonDao) {

    fun getAllPokemon(): Flow<List<PokemonEntity>> = pokemonDao.getAllPokemon()

    fun getDetailPokemon(idPokemon: String): Flow<PokemonEntity> = pokemonDao.getDetailPokemon(idPokemon)

    fun getSearchPokemon(keyword: String): Flow<List<PokemonEntity>> = pokemonDao.getSearchPokemon(keyword)

    fun getFavoritePokemon(): Flow<List<PokemonEntity>> = pokemonDao.getFavoritePokemon()

    suspend fun insertPokemon(pokemonList: List<PokemonEntity>) = pokemonDao.insertPokemon(pokemonList)

    suspend fun updatePokemonDetail(pokemonId: String,
                                    imageFrontDefault: String?,
                                    imageFrontShiny: String?,
                                    imageBackDefault: String?,
                                    imageBackShiny: String?,
                                    baseExperience: Int?,
                                    height: Int?,
                                    weight: Int?,
                                    order: Int?)
        = pokemonDao.updatePokemonDetail(pokemonId, imageFrontDefault, imageFrontShiny, imageBackDefault, imageBackShiny, baseExperience, height, weight, order)

    fun setFavoritePokemon(pokemon: PokemonEntity, newState: Boolean) {
        pokemon.isFavorite = newState
        pokemonDao.updateFavoritePokemon(pokemon)
    }
}