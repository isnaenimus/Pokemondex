package com.dicoding.pokedex.detail

import androidx.lifecycle.*
import com.dicoding.pokedex.core.data.Resource
import com.dicoding.pokedex.core.domain.model.Pokemon
import com.dicoding.pokedex.core.domain.usecase.PokemonUseCase

class DetailPokemonViewModel(private val pokemonUseCase: PokemonUseCase) : ViewModel() {

    private val idPokemon = MutableLiveData<String>()

    fun setDetailPokemon(id: String){
        this.idPokemon.value = id
    }

    var pokemonDetail: LiveData<Resource<Pokemon>> =
        Transformations.switchMap(idPokemon){
            pokemonUseCase.getDetailPokemon(it).asLiveData()
        }
}