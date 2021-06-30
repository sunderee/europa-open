package com.peteralexbizjak.europaopen.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.api.models.travel.TravelModel
import com.peteralexbizjak.europaopen.databinding.TravelListItemBinding

internal class TravelAdapter(
    private val travelList: List<TravelModel>
) : RecyclerView.Adapter<TravelAdapter.ViewHolder>() {
    internal class ViewHolder(
        private val binding: TravelListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelModel: TravelModel) {
            binding.countryCode = travelModel.countryCode
            binding.travelListItemRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = TravelSubAdapter(travelModel.data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = TravelListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(travelList[position])

    override fun getItemCount(): Int = travelList.size
}