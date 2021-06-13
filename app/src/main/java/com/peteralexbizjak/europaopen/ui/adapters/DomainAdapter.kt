package com.peteralexbizjak.europaopen.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.databinding.DomainListItemBinding
import com.peteralexbizjak.europaopen.ui.MeasuresInfoFragmentDirections
import com.peteralexbizjak.europaopen.ui.viewmodels.MeasuresViewModel

internal class DomainAdapter : RecyclerView.Adapter<DomainAdapter.ViewHolder>() {
    private val domainData = mutableListOf<MeasuresViewModel.Indicator>()

    internal class ViewHolder(
        private val binding: DomainListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(domainModel: MeasuresViewModel.Indicator) {
            binding.title = domainModel.title
            binding.root.setOnClickListener {
                if (domainModel.rules.isEmpty()) {
                    it.findNavController().navigate(
                        MeasuresInfoFragmentDirections.actionMeasuresInfoFragmentToRuleSingleFragment(
                            domainModel.title,
                            domainModel.comment ?: ""
                        )
                    )
                } else {
                    it.findNavController().navigate(
                        MeasuresInfoFragmentDirections.actionMeasuresInfoFragmentToRuleFragment(
                            domainModel.rules.toTypedArray()
                        )
                    )
                }
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = DomainListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(domainData[position])

    override fun getItemCount(): Int = domainData.size

    fun setNewData(newData: List<MeasuresViewModel.Indicator>) {
        notifyDataSetChanged()
        domainData.apply {
            clear()
            addAll(newData)
        }
    }
}