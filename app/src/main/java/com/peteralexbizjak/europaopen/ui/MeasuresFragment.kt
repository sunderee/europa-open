package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peteralexbizjak.europaopen.databinding.FragmentMeasuresBinding
import com.peteralexbizjak.europaopen.viewmodels.MeasuresViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MeasuresFragment : Fragment() {
    private val measuresViewModel by sharedViewModel<MeasuresViewModel>()

    private var bindingInstance: FragmentMeasuresBinding? = null
    private val binding get() = bindingInstance!!

    private lateinit var country: String
    private lateinit var countryCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            country = it.getString("country").toString()
            countryCode = it.getString("code").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentMeasuresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}