package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.databinding.FragmentMeasuresBinding
import com.peteralexbizjak.europaopen.ui.adapters.RegionsAdapter
import com.peteralexbizjak.europaopen.viewmodels.MeasuresViewModel
import com.peteralexbizjak.europaopen.viewmodels.models.GenericResponse
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MeasuresFragment : Fragment() {
    private val measuresViewModel by sharedViewModel<MeasuresViewModel>()

    private var bindingInstance: FragmentMeasuresBinding? = null
    private val binding get() = bindingInstance!!

    private lateinit var country: String
    private lateinit var countryCode: String

    private val recyclerViewAdapter = RegionsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            country = it.getString("country").toString()
            countryCode = it.getString("code").toString()
        }
        measuresViewModel.requestRegionsData(countryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentMeasuresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fragmentMeasuresStats.visibility = View.GONE
            fragmentMeasuresStatsRecyclerView.visibility = View.GONE
            fragmentMeasuresStatsRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = recyclerViewAdapter
            }
        }
        measuresViewModel.observeRegionData().observe(viewLifecycleOwner) {
            when (it) {
                is GenericResponse.Success -> {
                    binding.apply {
                        fragmentMeasuresProgressBar.visibility = View.GONE
                        fragmentMeasuresStats.visibility = View.VISIBLE
                        fragmentMeasuresStatsRecyclerView.visibility = View.VISIBLE
                    }
                    recyclerViewAdapter.setNewData(it.data)
                }
                is GenericResponse.Loading -> {
                    Snackbar.make(binding.root, "Loading...", Snackbar.LENGTH_SHORT).show()
                }
                is GenericResponse.Error -> {
                    Snackbar.make(binding.root, it.exception, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}