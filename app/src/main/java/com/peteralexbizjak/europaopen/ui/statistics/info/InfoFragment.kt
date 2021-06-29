package com.peteralexbizjak.europaopen.ui.statistics.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.databinding.FragmentInfoBinding
import com.peteralexbizjak.europaopen.models.GenericResponse
import com.peteralexbizjak.europaopen.ui.adapters.RuleAdapter
import com.peteralexbizjak.europaopen.ui.statistics.StatisticsViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.properties.Delegates

class InfoFragment : Fragment() {
    private var bindingInstance: FragmentInfoBinding? = null
    private val binding get() = bindingInstance!!

    private lateinit var domainName: String
    private var domainID by Delegates.notNull<Int>()

    private val statisticsViewModel by sharedViewModel<StatisticsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            domainName = it.getString("domain") ?: ""
            domainID = it.getInt("domain_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statisticsViewModel.domainData.observe(viewLifecycleOwner) {
            when (it) {
                is GenericResponse.Success -> {
                    binding.fragmentInfoRecyclerView.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = RuleAdapter(
                            it.data
                                .first { domain -> domain.id == domainID }
                                .indicators
                        )
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