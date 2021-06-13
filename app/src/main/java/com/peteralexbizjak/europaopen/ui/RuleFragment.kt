package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.peteralexbizjak.europaopen.databinding.FragmentRuleBinding
import com.peteralexbizjak.europaopen.ui.adapters.RuleAdapter
import com.peteralexbizjak.europaopen.ui.viewmodels.MeasuresViewModel

class RuleFragment : Fragment() {
    private var bindingInstance: FragmentRuleBinding? = null
    private val binding get() = bindingInstance!!

    private var recyclerViewAdapter: RuleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rulesData = arguments?.getParcelableArrayList<MeasuresViewModel.Rule>("data")
        recyclerViewAdapter = rulesData?.let { RuleAdapter(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentRuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentRuleRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
    }
}