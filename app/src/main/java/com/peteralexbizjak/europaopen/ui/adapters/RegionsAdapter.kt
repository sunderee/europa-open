package com.peteralexbizjak.europaopen.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.api.models.region.RegionModel
import com.peteralexbizjak.europaopen.databinding.RegionListItemBinding


internal class RegionsAdapter : RecyclerView.Adapter<RegionsAdapter.ViewHolder>() {
    private val regionData = mutableListOf<RegionModel>()

    internal class ViewHolder(
        private val binding: RegionListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(regionModel: RegionModel) {
            binding.region = regionModel.region
            binding.color = regionModel.color
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RegionListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(regionData[position])
    }

    override fun getItemCount(): Int = regionData.size

    fun setNewData(newData: List<RegionModel>) {
        notifyDataSetChanged()
        regionData.apply {
            clear()
            addAll(newData)
        }
    }
}