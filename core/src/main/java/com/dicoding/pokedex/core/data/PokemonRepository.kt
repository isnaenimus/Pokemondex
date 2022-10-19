package com.dicoding.pokedex.core.data

import com.dicoding.pokedex.core.data.source.local.LocalDataSource
import com.dicoding.pokedex.core.data.source.remote.RemoteDataSource
import com.dicoding.pokedex.core.data.source.remote.network.ApiResponse
import com.dicoding.pokedex.core.data.source.remote.response.DetailPokemonResponse
import com.dicoding.pokedex.core.data.source.remote.response.PokemonResponse
import com.dicoding.pokedex.core.domain.model.Pokemon
import com.dicoding.pokedex.core.domain.repository.IPokemonRepository
import com.dicoding.pokedex.core.utils.AppExecutors
import com.dicoding.pokedex.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IPokemonRepository {

    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, List<PokemonResponse>>() {
            override fun loadFromDB(): Flow<List<Pokemon>> {
                return localDataSource.getAllPokemon().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Pokemon>?) = data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<PokemonResponse>>> =
                remoteDataSource.getAllPokemon()

            override suspend fun saveCallResult(data: List<PokemonResponse>) {
                val pokemonList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPokemon(pokemonList)
            }
        }.asFlow()

    override fun getDetailPokemon(id: String): Flow<Resource<Pokemon>> =
        object : NetworkBoundResource<Pokemon, DetailPokemonResponse>() {
            override fun loadFromDB(): Flow<Pokemon> {
                return localDataSource.getDetailPokemon(id).map {
                    DataMapper.mapEntityToDomain(it)
                }
            }

            override fun shouldFetch(data: Pokemon?) = data?.height == null

            override suspend fun createCall(): Flow<ApiResponse<DetailPokemonResponse>> =
                remoteDataSource.getPokemonDetail(id)

            override suspend fun saveCallResult(data: DetailPokemonResponse) {
                localDataSource.updatePokemonDetail(
                    id,
                    data.sprites.front_default,
                    data.sprites.front_shiny,
                    data.sprites.back_default,
                    data.sprites.back_shiny,
                    data.base_experience,
                    data.height,
                    data.weight,
                    data.order
                )
            }
        }.asFlow()

    override fun getSearchPokemon(keyword: String): Flow<List<Pokemon>> {
        return localDataSource.getSearchPokemon(keyword).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoritePokemon(): Flow<List<Pokemon>> {
        return localDataSource.getFavoritePokemon().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) {
        val pokemonEntity = DataMapper.mapDomainToEntity(pokemon)
        appExecutors.diskIO().execute { localDataSource.setFavoritePokemon(pokemonEntity, state) }
    }
}