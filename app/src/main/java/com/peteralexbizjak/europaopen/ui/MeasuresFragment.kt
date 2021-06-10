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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentMeasuresBinding.inflate(inflater, container, false)
        return binding.root
    }
}