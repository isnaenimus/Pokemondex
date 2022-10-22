package com.dicoding.pokedex.core.di

import androidx.room.Room
import com.dicoding.pokedex.core.data.PokemonRepository
import com.dicoding.pokedex.core.data.source.local.LocalDataSource
import com.dicoding.pokedex.core.data.source.local.room.PokemonDatabase
import com.dicoding.pokedex.core.data.source.remote.RemoteDataSource
import com.dicoding.pokedex.core.data.source.remote.network.ApiService
import com.dicoding.pokedex.core.domain.repository.IPokemonRepository
import com.dicoding.pokedex.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<PokemonDatabase>().pokemonDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java, "Pokemon.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "pokeapi.co"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/D7nVX3nnTE0NXEmTWhim6nTMVFFIJWRuXenQGaeSRIw=")
            .add(hostname, "sha256/FEzVOUp4dF3gI0ZVPRJhFbSJVXR+uQmMH65xhs1glH4=")
            .add(hostname, "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IPokemonRepository> { PokemonRepository(get(), get(), get()) }
}
