package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.R
import com.peteralexbizjak.europaopen.databinding.FragmentMainBinding
import com.peteralexbizjak.europaopen.viewmodels.CountryViewModel
import com.peteralexbizjak.europaopen.viewmodels.models.GenericResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val countryViewModel by viewModel<CountryViewModel>()

    private var bindingInstance: FragmentMainBinding? = null
    private val binding get() = bindingInstance!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fragmentMainCountry.visibility = View.GONE
            fragmentMainCountryAutocomplete.visibility = View.GONE
            fragmentMainCountryState.visibility = View.GONE
            fragmentMainDestinationCountry.visibility = View.GONE
            fragmentMainDestinationCountryAutocomplete.visibility = View.GONE
            fragmentMainRequestCountryInfo.visibility = View.GONE
            fragmentMainRequestInfo.visibility = View.GONE
            fragmentMainSeparator.visibility = View.GONE
            fragmentMainStartingCountry.visibility = View.GONE
            fragmentMainStartingCountryAutocomplete.visibility = View.GONE
            fragmentMainTransitionCountry.visibility = View.GONE
            fragmentMainTransitionCountryAutocomplete.visibility = View.GONE
            fragmentMainTripPlanText.visibility = View.GONE
        }
        countryViewModel.getCountries().observe(viewLifecycleOwner) { it ->
            when (it) {
                is GenericResponse.Loading -> {
                    Snackbar.make(binding.root, "Loading", Snackbar.LENGTH_SHORT).show()
                }
                is GenericResponse.Error -> {
                    Snackbar.make(binding.root, it.exception, Snackbar.LENGTH_SHORT).show()
                }
                is GenericResponse.Success -> {
                    binding.apply {
                        fragmentMainProgressBar.visibility = View.GONE

                        fragmentMainCountry.visibility = View.VISIBLE
                        fragmentMainCountryAutocomplete.visibility = View.VISIBLE
                        fragmentMainCountryState.visibility = View.VISIBLE
                        fragmentMainDestinationCountry.visibility = View.VISIBLE
                        fragmentMainDestinationCountryAutocomplete.visibility = View.VISIBLE
                        fragmentMainRequestCountryInfo.visibility = View.VISIBLE
                        fragmentMainRequestInfo.visibility = View.VISIBLE
                        fragmentMainSeparator.visibility = View.VISIBLE
                        fragmentMainStartingCountry.visibility = View.VISIBLE
                        fragmentMainStartingCountryAutocomplete.visibility = View.VISIBLE
                        fragmentMainTransitionCountry.visibility = View.VISIBLE
                        fragmentMainTransitionCountryAutocomplete.visibility = View.VISIBLE
                        fragmentMainTripPlanText.visibility = View.VISIBLE
                    }

                    binding.fragmentMainStartingCountryAutocomplete.setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            R.layout.list_item,
                            it.data
                                .filter { model -> model.direction == "from" || model.direction == "both" }
                                .sortedBy { item -> item.shortName }
                                .map { item -> item.longName }
                        )
                    )
                    binding.fragmentMainDestinationCountryAutocomplete.setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            R.layout.list_item,
                            it.data
                                .filter { model -> model.direction == "to" || model.direction == "both" }
                                .sortedBy { item -> item.shortName }
                                .map { item -> item.longName }
                        )
                    )
                    binding.fragmentMainTransitionCountryAutocomplete.setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            R.layout.list_item,
                            it.data
                                .filter { model -> model.direction == "to" || model.direction == "both" }
                                .sortedBy { item -> item.shortName }
                                .map { item -> item.longName }
                        )
                    )
                    binding.fragmentMainCountryAutocomplete.setAdapter(
                        ArrayAdapter(
                            requireContext(),
                            R.layout.list_item,
                            it.data
                                .filter { model -> model.direction == "both" }
                                .sortedBy { item -> item.shortName }
                                .map { item -> item.longName }
                        )
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