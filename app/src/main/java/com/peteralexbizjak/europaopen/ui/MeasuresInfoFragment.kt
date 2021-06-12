package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.databinding.FragmentMeasuresInfoBinding
import com.peteralexbizjak.europaopen.ui.adapters.DomainAdapter
import com.peteralexbizjak.europaopen.ui.viewmodels.MeasuresViewModel
import com.peteralexbizjak.europaopen.ui.viewmodels.models.GenericResponse
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.properties.Delegates

class MeasuresInfoFragment : Fragment() {
    private val measuresViewModel by sharedViewModel<MeasuresViewModel>()

    private var bindingInstance: FragmentMeasuresInfoBinding? = null
    private val binding get() = bindingInstance!!

    private lateinit var country: String
    private lateinit var countryCode: String
    private var domainCode by Delegates.notNull<Int>()

    private val recyclerViewAdapter = DomainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            country = it.getString("country").toString()
            countryCode = it.getString("code").toString()
            domainCode = it.getInt("domain")
        }
        measuresViewModel.requestDomainData(countryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentMeasuresInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentMeasuresInfoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
        measuresViewModel.observeDomainData().observe(viewLifecycleOwner) {
            when (it) {
                is GenericResponse.Success -> {
                    binding.fragmentMeasuresInfoProgressBar.visibility = View.GONE
                    recyclerViewAdapter.setNewData(
                        it.data
                            .filter { domain -> domain.title == DOMAIN_DATA[domainCode] }
                            .flatMap { domain -> domain.indicators }
                    )
                }
                is GenericResponse.Loading -> {
                    Snackbar.make(binding.root, "Loading", Snackbar.LENGTH_SHORT).show()
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

    companion object {
        val DOMAIN_DATA = mapOf(
            7 to "Travel",
            6 to "Coronavirus Measures",
            5 to "Health Situation",
            8 to "Other Info"
        )
    }
}