package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.peteralexbizjak.europaopen.R
import com.peteralexbizjak.europaopen.api.models.country.CountryModel
import com.peteralexbizjak.europaopen.databinding.FragmentStatisticsBinding

@Suppress("UNCHECKED_CAST")
class StatisticsFragment : Fragment() {
    private var bindingInstance: FragmentStatisticsBinding? = null
    private val binding get() = bindingInstance!!

    private lateinit var countries: Array<CountryModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            countries = it.getParcelableArray("countries") as Array<CountryModel>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentStatisticsCountryTextView.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.list_item,
                countries
                    .sortedBy { item -> item.shortName }
                    .map { item -> item.longName }
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}