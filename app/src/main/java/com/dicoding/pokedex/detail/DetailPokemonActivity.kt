package com.dicoding.pokedex.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dicoding.pokedex.R
import com.dicoding.pokedex.core.data.Resource
import com.dicoding.pokedex.core.domain.model.Pokemon
import com.dicoding.pokedex.databinding.ActivityDetailPokemonBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPokemonActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private val detailPokemonViewModel: DetailPokemonViewModel by viewModel()
    private lateinit var binding: ActivityDetailPokemonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE


        //setSupportActionBar(binding.toolbar)

        val pokemon = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_DATA, Pokemon::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_DATA)
        }
        observeDetailPokemon(pokemon)
    }

    private fun observeDetailPokemon(pokemon: Pokemon?) {
        pokemon?.let { pokemon ->
            detailPokemonViewModel.setDetailPokemon(pokemon.id)
            detailPokemonViewModel.pokemonDetail.observe(this) { pokemonDetail ->
                if (pokemonDetail != null) {
                    when (pokemonDetail) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            showDetailPokemon(pokemonDetail)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                pokemonDetail.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }

    }

    private fun showDetailPokemon(pokemonDetail: Resource<Pokemon>) {
        pokemonDetail.data?.let {
            with(binding) {
                tvTitle.text = it.name
                tvExperience.text = it.baseExperience.toString()
                tvHeight.text = it.height.toString()
                tvWeight.text = it.weight.toString()
                tvOrder.text = it.order.toString()

                Glide.with(this@DetailPokemonActivity)
                    .load(it.imageFrontDefault)
                    .into(ivFrontDefault)

                Glide.with(this@DetailPokemonActivity)
                    .load(it.imageFrontShiny)
                    .into(ivFrontShiny)

                Glide.with(this@DetailPokemonActivity)
                    .load(it.imageBackDefault)
                    .into(ivBackDefault)

                Glide.with(this@DetailPokemonActivity)
                    .load(it.imageBackShiny)
                    .into(ivBackShiny)
            }
        }
    }

}