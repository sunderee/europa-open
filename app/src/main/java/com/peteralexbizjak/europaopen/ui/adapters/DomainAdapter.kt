package com.peteralexbizjak.europaopen.ui.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.peteralexbizjak.europaopen.databinding.DomainListItemBinding
import com.peteralexbizjak.europaopen.viewmodels.MeasuresViewModel

internal class DomainAdapter : RecyclerView.Adapter<DomainAdapter.ViewHolder>() {
    private val domainData = mutableListOf<MeasuresViewModel.Domain>()

    internal class ViewHolder(
        private val binding: DomainListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(domainModel: MeasuresViewModel.Domain) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    fun setNewData(newData: List<MeasuresViewModel.Domain>) {
        notifyDataSetChanged()
        domainData.apply {
            clear()
            addAll(newData)
        }
    }
}