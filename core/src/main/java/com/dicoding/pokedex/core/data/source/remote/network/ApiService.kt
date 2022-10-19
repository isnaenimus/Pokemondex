package com.dicoding.pokedex.core.data.source.remote.network

import com.dicoding.pokedex.core.data.source.remote.response.DetailPokemonResponse
import com.dicoding.pokedex.core.data.source.remote.response.ListPokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): ListPokemonResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: String?
    ): DetailPokemonResponse
}