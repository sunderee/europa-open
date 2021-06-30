package com.peteralexbizjak.europaopen.ui.travel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.databinding.FragmentTravelInfoBinding
import com.peteralexbizjak.europaopen.models.GenericResponse
import com.peteralexbizjak.europaopen.ui.adapters.TravelAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TravelInfoFragment : Fragment() {
    private var bindingInstance: FragmentTravelInfoBinding? = null
    private val binding get() = bindingInstance!!

    private val travelViewModel by sharedViewModel<TravelViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentTravelInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            fragmentTravelInfoToolbar.visibility = View.GONE
            fragmentTravelInfoRecyclerView.visibility = View.GONE
        }
        travelViewModel.travelData.observe(viewLifecycleOwner) {
            when (it) {
                is GenericResponse.Success -> {
                    binding.apply {
                        fragmentTravelInfoToolbar.visibility = View.VISIBLE
                        fragmentTravelInfoToolbar.title = it
                            .data
                            .joinToString("/") { element -> element.countryCode }
                        fragmentTravelInfoRecyclerView.visibility = View.VISIBLE
                        fragmentTravelInfoProgressBar.visibility = View.GONE
                    }

                    binding.fragmentTravelInfoRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = TravelAdapter(it.data)
                    }
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
}