package com.dicoding.pokedex.core.utils

import com.dicoding.pokedex.core.data.source.local.entity.PokemonEntity
import com.dicoding.pokedex.core.data.source.remote.response.PokemonResponse
import com.dicoding.pokedex.core.domain.model.Pokemon

object DataMapper {
    fun mapResponsesToEntities(input: List<PokemonResponse>): List<PokemonEntity> {
        val pokemonList = ArrayList<PokemonEntity>()
        input.map {
            // remove last slash from pokemon url
            val pokemonUrl = it.url.replaceFirst(".$".toRegex(), "")
            // get pokemon id
            val pokemonId = pokemonUrl.split('/').last()
            val pokemonImageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

            val pokemon = PokemonEntity(
                id = pokemonId,
                name = it.name,
                url = it.url,
                imageFrontDefault = pokemonImageUrl,
                imageFrontShiny = null,
                imageBackDefault = null,
                imageBackShiny = null,
                baseExperience = null,
                height = null,
                weight = null,
                order = null,
                isFavorite = false
            )
            pokemonList.add(pokemon)
        }
        return pokemonList
    }

    fun mapEntitiesToDomain(input: List<PokemonEntity>): List<Pokemon> =
        input.map {
            Pokemon(
                id = it.id,
                name = it.name,
                url = it.url,
                imageFrontDefault = it.imageFrontDefault,
                imageFrontShiny = it.imageFrontShiny,
                imageBackDefault = it.imageBackDefault,
                imageBackShiny = it.imageBackShiny,
                baseExperience = it.baseExperience,
                height = it.height,
                weight = it.weight,
                order = it.order,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntityToDomain(input: PokemonEntity): Pokemon =
        Pokemon(
            id = input.id,
            name = input.name,
            url = input.url,
            imageFrontDefault = input.imageFrontDefault,
            imageFrontShiny = input.imageFrontShiny,
            imageBackDefault = input.imageBackDefault,
            imageBackShiny = input.imageBackShiny,
            baseExperience = input.baseExperience,
            height = input.height,
            weight = input.weight,
            order = input.order,
            isFavorite = input.isFavorite
        )

    fun mapDomainToEntity(input: Pokemon) = PokemonEntity(
        id = input.id,
        name = input.name,
        url = input.url,
        imageFrontDefault = input.imageFrontDefault,
        imageFrontShiny = input.imageFrontShiny,
        imageBackDefault = input.imageBackDefault,
        imageBackShiny = input.imageBackShiny,
        baseExperience = input.baseExperience,
        height = input.height,
        weight = input.weight,
        order = input.order,
        isFavorite = input.isFavorite
    )
}