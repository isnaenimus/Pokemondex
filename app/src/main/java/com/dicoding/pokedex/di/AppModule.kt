package com.dicoding.pokedex.di

import com.dicoding.pokedex.core.domain.usecase.PokemonInteractor
import com.dicoding.pokedex.core.domain.usecase.PokemonUseCase
import com.dicoding.pokedex.detail.DetailPokemonViewModel
import com.dicoding.pokedex.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<PokemonUseCase> { PokemonInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailPokemonViewModel(get()) }
}