package com.peteralexbizjak.europaopen.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.databinding.RuleListItemBinding
import com.peteralexbizjak.europaopen.models.statistics.Indicator

internal class RuleAdapter(
    private val indicatorsList: List<Indicator>
) : RecyclerView.Adapter<RuleAdapter.ViewHolder>() {
    internal class ViewHolder(
        private val binding: RuleListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(indicator: Indicator) {
            binding.apply {
                title = indicator.title
                if (indicator.rules.isEmpty()) {
                    ruleListItemRecyclerView.visibility = View.GONE
                    contents = indicator.comment
                } else {
                    ruleListItemContents.visibility = View.GONE
                    ruleListItemRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = RuleSubAdapter(indicator.rules)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RuleListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(indicatorsList[position])

    override fun getItemCount(): Int = indicatorsList.size
}