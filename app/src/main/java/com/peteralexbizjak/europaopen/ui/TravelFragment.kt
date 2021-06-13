package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peteralexbizjak.europaopen.databinding.FragmentTravelBinding

class TravelFragment : Fragment() {
    private var bindingInstance: FragmentTravelBinding? = null
    private val binding get() = bindingInstance!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentTravelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }

}