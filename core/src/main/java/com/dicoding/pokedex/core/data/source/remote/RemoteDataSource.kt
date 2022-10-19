package com.dicoding.pokedex.core.data.source.remote

import android.util.Log
import com.dicoding.pokedex.core.data.source.remote.network.ApiResponse
import com.dicoding.pokedex.core.data.source.remote.network.ApiService
import com.dicoding.pokedex.core.data.source.remote.response.DetailPokemonResponse
import com.dicoding.pokedex.core.data.source.remote.response.PokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllPokemon(): Flow<ApiResponse<List<PokemonResponse>>> {
        return flow {
            try {
                val response = apiService.getPokemonList()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPokemonDetail(id: String): Flow<ApiResponse<DetailPokemonResponse>> {
        return flow {
            try {
                val response = apiService.getPokemonDetail(id)
                emit(ApiResponse.Success(response))
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}