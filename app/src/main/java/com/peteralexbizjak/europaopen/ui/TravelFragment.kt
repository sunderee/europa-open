package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.databinding.FragmentTravelBinding
import com.peteralexbizjak.europaopen.ui.adapters.TravelAdapter
import com.peteralexbizjak.europaopen.ui.viewmodels.TravelViewModel
import com.peteralexbizjak.europaopen.ui.viewmodels.models.GenericResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class TravelFragment : Fragment() {
    private val travelViewModel by viewModel<TravelViewModel>()

    private var bindingInstance: FragmentTravelBinding? = null
    private val binding get() = bindingInstance!!

    private val recyclerViewAdapter = TravelAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        travelViewModel.requestTravelInfo(arguments?.getString("destination").toString())
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
        binding.fragmentTravelRecyclerView.visibility = View.GONE
        binding.fragmentTravelRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
        travelViewModel.observeTravelInfo().observe(viewLifecycleOwner) {
            if (it is GenericResponse.Success) {
                binding.apply {
                    fragmentTravelRecyclerView.visibility = View.VISIBLE
                    fragmentTravelProgressBar.visibility = View.GONE
                }
                recyclerViewAdapter.setNewData(it.data)
            } else {
                Snackbar.make(binding.root, "Loading", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }

}