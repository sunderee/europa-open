package com.peteralexbizjak.europaopen.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.R
import com.peteralexbizjak.europaopen.databinding.FragmentStatisticsInfoBinding
import com.peteralexbizjak.europaopen.models.GenericResponse
import com.peteralexbizjak.europaopen.ui.adapters.RegionsAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StatisticsInfoFragment : Fragment() {
    private var bindingInstance: FragmentStatisticsInfoBinding? = null
    private val binding get() = bindingInstance!!

    private lateinit var country: String

    private val statisticsViewModel by sharedViewModel<StatisticsViewModel>()

    private val recyclerViewAdapter = RegionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            country = it.getString("country") ?: ""
        }
    }

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
        binding.fragmentStatisticsInfoContent.visibility = View.GONE
        binding.fragmentStatisticsInfoToolbar.setNavigationOnClickListener {
            binding.root.findNavController().popBackStack(R.id.landingFragment, false)
        }

        binding.fragmentStatisticsInfoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }

        binding.country = country

        statisticsViewModel.regionData.observe(viewLifecycleOwner) {
            when (it) {
                is GenericResponse.Success -> {
                    binding.apply {
                        fragmentStatisticsInfoContent.visibility = View.VISIBLE
                        fragmentStatisticsInfoProgressBar.visibility = View.GONE

                        // On click listeners
                        fragmentStatisticsInfoTravelCard.setOnClickListener {
                            root.findNavController().navigate(
                                StatisticsInfoFragmentDirections.actionStatisticsInfoFragmentToInfoFragment(
                                    "Travel",
                                    TRAVEL_DOMAIN_ID
                                )
                            )
                        }

                        fragmentStatisticsInfoMeasuresCard.setOnClickListener {
                            root.findNavController().navigate(
                                StatisticsInfoFragmentDirections.actionStatisticsInfoFragmentToInfoFragment(
                                    "Measures",
                                    MEASURES_DOMAIN_ID
                                )
                            )
                        }

                        fragmentStatisticsInfoHealthSituationCard.setOnClickListener {
                            root.findNavController().navigate(
                                StatisticsInfoFragmentDirections.actionStatisticsInfoFragmentToInfoFragment(
                                    "Health Situation",
                                    HEALTH_DOMAIN_ID
                                )
                            )
                        }

                        fragmentStatisticsInfoInfoCard.setOnClickListener {
                            root.findNavController().navigate(
                                StatisticsInfoFragmentDirections.actionStatisticsInfoFragmentToInfoFragment(
                                    "Info",
                                    INFO_DOMAIN_ID
                                )
                            )
                        }
                    }
                    recyclerViewAdapter.setNewData(it.data)
                }
                is GenericResponse.Error -> {
                    Snackbar.make(binding.root, it.exception, Snackbar.LENGTH_SHORT).show()
                }
                is GenericResponse.Loading -> {
                    Snackbar.make(binding.root, "Loading", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }

    companion object {
        private const val TRAVEL_DOMAIN_ID = 7
        private const val MEASURES_DOMAIN_ID = 6
        private const val HEALTH_DOMAIN_ID = 5
        private const val INFO_DOMAIN_ID = 8
    }
}