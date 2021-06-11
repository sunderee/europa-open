package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
        binding.fragmentMainContent.visibility = View.GONE
        countryViewModel.observeCountries().observe(viewLifecycleOwner) { it ->
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
                        fragmentMainContent.visibility = View.VISIBLE
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

                    binding.fragmentMainRequestCountryInfo.setOnClickListener { _ ->
                        val country = binding.fragmentMainCountryAutocomplete.text.toString()
                        binding.root
                            .findNavController()
                            .navigate(
                                MainFragmentDirections.actionMainFragmentToMeasuresFragment(
                                    country = country,
                                    code = it.data.find { it.longName == country }?.shortName ?: ""
                                )
                            )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}