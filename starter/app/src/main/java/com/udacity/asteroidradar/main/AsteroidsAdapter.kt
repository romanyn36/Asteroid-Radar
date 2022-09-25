package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidBinding

class AsteroidsAdapter(val listener: AsteroidsListener) :ListAdapter<Asteroid, AsteroidsAdapter.AsteroidViewHolder>(AsteroidsDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
     return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item =getItem(position)
        holder.bind(item,listener)
    }

    class AsteroidViewHolder(val binding:AsteroidBinding):RecyclerView.ViewHolder(binding.root) {
        val res=itemView.context.resources
        fun bind(asteroid: Asteroid, listener: AsteroidsListener)
        {
            binding.asteroid=asteroid
            binding.litner=listener

        }
        companion object {
            fun from (parent: ViewGroup):AsteroidViewHolder
            {
                val layoutInflater =LayoutInflater.from(parent.context)
                val binding=AsteroidBinding.inflate(layoutInflater,parent,false)
                return AsteroidViewHolder(binding)

            }
        }

    }

}
class AsteroidsDiffCallBack:DiffUtil.ItemCallback<Asteroid>()
{
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem==newItem
    }

}
class AsteroidsListener (val clickListener: (asteroidId: Asteroid) -> Unit)
{
    fun click(asterod: Asteroid)=clickListener(asterod)
}

