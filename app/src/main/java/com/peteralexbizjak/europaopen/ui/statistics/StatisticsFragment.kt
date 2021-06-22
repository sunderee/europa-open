package com.peteralexbizjak.europaopen.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.R
import com.peteralexbizjak.europaopen.api.models.country.CountryModel
import com.peteralexbizjak.europaopen.databinding.FragmentStatisticsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@Suppress("UNCHECKED_CAST")
class StatisticsFragment : Fragment() {
    private var bindingInstance: FragmentStatisticsBinding? = null
    private val binding get() = bindingInstance!!

    private lateinit var countries: Array<CountryModel>
    private val statisticsViewModel by sharedViewModel<StatisticsViewModel>()

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

        binding.fragmentStatisticsGo.setOnClickListener {
            val selectedCountry = binding.fragmentStatisticsCountryTextView.text.toString()
            if (selectedCountry.isEmpty()) {
                Snackbar.make(it, "No country selected", Snackbar.LENGTH_SHORT).show()
            }

            val getShortName = countries
                .find { country -> country.longName == selectedCountry }
                ?.shortName
            if (getShortName == null) {
                Snackbar.make(it, "No country selected", Snackbar.LENGTH_SHORT).show()
            }

            statisticsViewModel.apply {
                getShortName?.let { name ->
                    requestDomainData(
                        name,
                        binding.fragmentStatisticsCheckbox.isChecked
                    )
                }
                getShortName?.let { name ->
                    requestRegionsData(
                        name,
                        binding.fragmentStatisticsCheckbox.isChecked
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}