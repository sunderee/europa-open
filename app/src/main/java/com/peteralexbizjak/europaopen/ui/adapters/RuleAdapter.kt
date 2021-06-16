package com.peteralexbizjak.europaopen.ui.adapters

import androidx.core.text.HtmlCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.databinding.RulesListItemBinding
import com.peteralexbizjak.europaopen.ui.viewmodels.models.parcelable.Rule

internal class RuleAdapter(
    private val ruleData: Array<Rule>
) : RecyclerView.Adapter<RuleAdapter.ViewHolder>() {

    internal class ViewHolder(
        private val binding: RulesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rule: Rule) {
            binding.title = rule.title
            binding.contents = HtmlCompat.fromHtml(rule.comment, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RulesListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(ruleData[position])

    override fun getItemCount(): Int = ruleData.size
}