package com.dicoding.pokedex.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.pokedex.core.domain.model.Pokemon
import com.dicoding.pokedex.core.domain.usecase.PokemonUseCase

class FavoriteViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    val pokemon = pokemonUseCase.getFavoritePokemon().asLiveData()
    fun setFavoritePokemon(pokemon: Pokemon, newStatus:Boolean) = pokemonUseCase.setFavoritePokemon(pokemon, newStatus)
}