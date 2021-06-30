package com.peteralexbizjak.europaopen.ui.travel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.peteralexbizjak.europaopen.R
import com.peteralexbizjak.europaopen.api.models.country.CountryModel
import com.peteralexbizjak.europaopen.databinding.FragmentTravelBinding

@Suppress("UNCHECKED_CAST")
class TravelFragment : Fragment() {
    private var bindingInstance: FragmentTravelBinding? = null
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
        bindingInstance = FragmentTravelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fragmentTravelStartingCountryAutocomplete.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.list_item,
                    countries
                        .filter { item -> item.direction == "from" || item.direction == "both" }
                        .sortedBy { item -> item.shortName }
                        .map { item -> item.longName }
                )
            )

            fragmentTravelDestinationCountryAutocomplete.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.list_item,
                    countries
                        .filter { item -> item.direction == "to" || item.direction == "both" }
                        .sortedBy { item -> item.shortName }
                        .map { item -> item.longName }
                )
            )

            fragmentTravelTransitionCountryAutocomplete.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.list_item,
                    countries
                        .filter { item -> item.direction == "both" }
                        .sortedBy { item -> item.shortName }
                        .map { item -> item.longName }
                )
            )

            fragmentTravelCheckbox.setOnCheckedChangeListener { _, isChecked -> }

            fragmentTravelButton.setOnClickListener {
                // TODO: request info
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}