package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peteralexbizjak.europaopen.databinding.FragmentLandingBinding
import com.peteralexbizjak.europaopen.utils.extensions.launchURL
import com.peteralexbizjak.europaopen.utils.extensions.sendEmail

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
            fragmentLandingCode.setOnClickListener {
                launchURL("https://github.com/sunderee/EuropaOpen", it)
            }
            fragmentLandingSubmitFeedback.setOnClickListener {
                sendEmail(
                    "Feedback on Europa Open",
                    arrayOf("peteralex.developer@gmail.com", "info.c2studios@gmail.com"),
                    "We are always looking forward to hearing from you.",
                    it
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}