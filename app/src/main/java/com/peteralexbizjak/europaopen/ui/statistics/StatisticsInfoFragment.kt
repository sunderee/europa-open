package com.peteralexbizjak.europaopen.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.peteralexbizjak.europaopen.R
import com.peteralexbizjak.europaopen.databinding.FragmentStatisticsInfoBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StatisticsInfoFragment : Fragment() {
    private var bindingInstance: FragmentStatisticsInfoBinding? = null
    private val binding get() = bindingInstance!!

    private val statisticsViewModel by sharedViewModel<StatisticsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentStatisticsInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentStatisticsInfoToolbar.setNavigationOnClickListener {
            binding.root.findNavController().popBackStack(R.id.landingFragment, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}