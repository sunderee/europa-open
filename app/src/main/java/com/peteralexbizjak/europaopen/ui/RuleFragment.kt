package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.peteralexbizjak.europaopen.databinding.FragmentRuleBinding
import com.peteralexbizjak.europaopen.ui.adapters.RuleAdapter

class RuleFragment : Fragment() {
    private var bindingInstance: FragmentRuleBinding? = null
    private val binding get() = bindingInstance!!

    private val navigationArguments by navArgs<RuleFragmentArgs>()

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
            adapter = navigationArguments.rules?.let { RuleAdapter(it) }
        }
    }
}