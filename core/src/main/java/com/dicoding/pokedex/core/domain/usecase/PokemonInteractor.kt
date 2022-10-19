package com.dicoding.pokedex.core.domain.usecase

import com.dicoding.pokedex.core.domain.model.Pokemon
import com.dicoding.pokedex.core.domain.repository.IPokemonRepository

class PokemonInteractor(private val pokemonRepository: IPokemonRepository): PokemonUseCase {

    override fun getAllPokemon() = pokemonRepository.getAllPokemon()

    override fun getDetailPokemon(id: String) = pokemonRepository.getDetailPokemon(id)

    override fun getSearchPokemon(keyword: String) = pokemonRepository.getSearchPokemon(keyword)

    override fun getFavoritePokemon() = pokemonRepository.getFavoritePokemon()

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) = pokemonRepository.setFavoritePokemon(pokemon, state)
}