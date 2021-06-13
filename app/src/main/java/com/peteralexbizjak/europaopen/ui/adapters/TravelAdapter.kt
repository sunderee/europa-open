package com.peteralexbizjak.europaopen.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.api.models.travel.TravelModel
import com.peteralexbizjak.europaopen.databinding.TravelListItemBinding
import com.peteralexbizjak.europaopen.ui.TravelFragmentDirections

internal class TravelAdapter : RecyclerView.Adapter<TravelAdapter.ViewHolder>() {
    private val travelData = mutableListOf<TravelModel>()

    internal class ViewHolder(
        private val binding: TravelListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(travelModel: TravelModel) {
            binding.country = travelModel.countryCode
            binding.root.setOnClickListener {
                it.findNavController()
                    .navigate(
                        TravelFragmentDirections.actionTravelFragmentToTravelInfoFragment(
                            travelModel.data.first()
                        )
                    )
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = TravelListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(travelData[position])

    override fun getItemCount(): Int = travelData.size

    fun setNewData(newData: List<TravelModel>) {
        notifyDataSetChanged()
        travelData.apply {
            clear()
            addAll(newData)
        }
    }
}