package com.peteralexbizjak.europaopen.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.api.models.measures.IndicatorModel
import com.peteralexbizjak.europaopen.databinding.RuleSubListItemBinding

internal class TravelSubAdapter(
    private val indicators: List<IndicatorModel>
) : RecyclerView.Adapter<TravelSubAdapter.ViewHolder>() {
    internal class ViewHolder(
        private val binding: RuleSubListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(indicator: IndicatorModel) {
            binding.title = indicator.indicator
            binding.contents = indicator.restrictions
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RuleSubListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(indicators[position])

    override fun getItemCount(): Int = indicators.size
}