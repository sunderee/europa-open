package com.peteralexbizjak.europaopen.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.databinding.RuleSubListItemBinding
import com.peteralexbizjak.europaopen.models.statistics.Rule

internal class RuleSubAdapter(
    private val rulesList: List<Rule>
) : RecyclerView.Adapter<RuleSubAdapter.ViewHolder>() {
    internal class ViewHolder(
        private val binding: RuleSubListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Rule) {
            binding.title = item.title
            binding.contents = item.comment
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RuleSubListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(rulesList[position])

    override fun getItemCount(): Int = rulesList.size
}