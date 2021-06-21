package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.peteralexbizjak.europaopen.R
import com.peteralexbizjak.europaopen.databinding.FragmentLandingBinding
import com.peteralexbizjak.europaopen.utils.extensions.launchURL

class LandingFragment : Fragment() {
    private var bindingInstance: FragmentLandingBinding? = null
    private val binding get() = bindingInstance!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentLandingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // On click listeners
        binding.apply {
            fragmentLandingSource.setOnClickListener {
                binding.root.findNavController().navigate(R.id.dataSourceFragment)
            }
            fragmentLandingCode.setOnClickListener {
                launchURL("https://github.com/sunderee/EuropaOpen", it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}