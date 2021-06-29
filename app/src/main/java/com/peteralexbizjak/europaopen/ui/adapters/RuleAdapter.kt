package com.peteralexbizjak.europaopen.ui.adapters

import android.content.Context
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
        private val context: Context,
        private val binding: RuleListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(indicator: Indicator) {
            println(indicator)
            binding.apply {
                title = indicator.title
                if (indicator.rules.isEmpty()) {
                    ruleListItemRecyclerView.visibility = View.GONE
                    contents = indicator.comment
                } else {
                    ruleListItemContents.visibility = View.GONE
                    ruleListItemRecyclerView.layoutManager = LinearLayoutManager(context)
                    ruleListItemRecyclerView.adapter = RuleSubAdapter(indicator.rules)
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RuleListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(parent.context, itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(indicatorsList[position])

    override fun getItemCount(): Int = indicatorsList.size
}