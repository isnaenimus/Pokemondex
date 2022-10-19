package com.dicoding.pokedex.core.domain.usecase

import com.dicoding.pokedex.core.data.Resource
import com.dicoding.pokedex.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>
    fun getDetailPokemon(id: String): Flow<Resource<Pokemon>>
    fun getSearchPokemon(keyword: String): Flow<List<Pokemon>>
    fun getFavoritePokemon(): Flow<List<Pokemon>>
    fun setFavoritePokemon(pokemon: Pokemon, state: Boolean)
}