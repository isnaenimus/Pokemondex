package com.dicoding.pokedex.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.pokedex.core.domain.model.Pokemon
import com.dicoding.pokedex.core.domain.usecase.PokemonUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class HomeViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    val queryChannel = MutableStateFlow("")
    val pokemon = pokemonUseCase.getAllPokemon().asLiveData()
    val searchResult = queryChannel
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            pokemonUseCase.getSearchPokemon(it)
        }
        .asLiveData()

    fun setFavoritePokemon(pokemon: Pokemon, newStatus:Boolean) = pokemonUseCase.setFavoritePokemon(pokemon, newStatus)
}