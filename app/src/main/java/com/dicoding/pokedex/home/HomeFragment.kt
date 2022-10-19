package com.dicoding.pokedex.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.pokedex.R
import com.dicoding.pokedex.core.data.Resource
import com.dicoding.pokedex.databinding.FragmentHomeBinding
import com.dicoding.pokedex.detail.DetailPokemonActivity
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dicoding.pokedex.core.ui.PokemonAdapter as PokemonAdapter

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private var pokemonAdapter = PokemonAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val edSearch: AutoCompleteTextView = binding.edSearch

        edSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch {
                    homeViewModel.queryChannel.value = s.toString()
                }
            }
        })

        observeSearchPokemon()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            pokemonAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailPokemonActivity::class.java)
                intent.putExtra(DetailPokemonActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }
            pokemonAdapter.setFavorite = { selectedData, statusFavorite ->
                homeViewModel.setFavoritePokemon(selectedData, statusFavorite)
            }

            observePokemon()

            with(binding.rvPokemon) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = pokemonAdapter
            }
        }
    }

    private fun observePokemon() {
        homeViewModel.pokemon.observe(viewLifecycleOwner) { pokemon ->
            if (pokemon != null) {
                when (pokemon) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        pokemonAdapter.setData(pokemon.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text =
                            pokemon.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
    }

    private fun observeSearchPokemon() {
        homeViewModel.searchResult.observe(viewLifecycleOwner) {
            pokemonAdapter.setData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}