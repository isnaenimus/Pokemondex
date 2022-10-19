package com.dicoding.pokedex.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.pokedex.core.ui.PokemonAdapter
import com.dicoding.pokedex.databinding.FragmentFavoriteBinding
import com.dicoding.pokedex.detail.DetailPokemonActivity
import com.dicoding.pokedex.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private var pokemonAdapter = PokemonAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadKoinModules(favoriteModule)

        if (activity != null) {

            pokemonAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailPokemonActivity::class.java)
                intent.putExtra(DetailPokemonActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }
            pokemonAdapter.setFavorite = { selectedData, statusFavorite ->
                favoriteViewModel.setFavoritePokemon(selectedData, statusFavorite)
            }

            observePokemon()

            with(binding.rvPokemon) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = pokemonAdapter
            }
        }
    }

    private fun observePokemon() {
        favoriteViewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            pokemonAdapter.setData(pokemon)
            binding.viewEmpty.root.visibility = if (pokemon.isNotEmpty()) View.GONE else View.VISIBLE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}