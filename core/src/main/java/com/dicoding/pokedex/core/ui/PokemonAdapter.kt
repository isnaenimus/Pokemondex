package com.dicoding.pokedex.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.pokedex.core.R
import com.dicoding.pokedex.core.databinding.ItemListPokemonBinding
import com.dicoding.pokedex.core.domain.model.Pokemon

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.ListViewHolder>() {

    private var listData = ArrayList<Pokemon>()
    var onItemClick: ((Pokemon) -> Unit)? = null
    var setFavorite: ((Pokemon, statusFavorite: Boolean) -> Unit)? = null

    fun setData(newListData: List<Pokemon>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_pokemon, parent, false))

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListPokemonBinding.bind(itemView)
        fun bind(data: Pokemon) {
            var statusFavorite = data.isFavorite
            setStatusFavorite(data.isFavorite)
            with(binding) {
                tvItemTitle.text = data.name
                fabFavorite.setOnClickListener {
                    statusFavorite = !statusFavorite
                    setFavorite?.invoke(listData[adapterPosition], statusFavorite)
                    setStatusFavorite(statusFavorite)
                }
                Glide
                    .with(itemView.context)
                    .load(data.imageFrontDefault)
                    .into(binding.ivPokemon)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }

        private fun setStatusFavorite(statusFavorite: Boolean) {
            if (statusFavorite) {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_favorite_red))
            } else {
                binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_not_favorite))
            }
        }
    }
}