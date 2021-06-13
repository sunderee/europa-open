package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.peteralexbizjak.europaopen.databinding.FragmentTravelInfoBinding

class TravelInfoFragment : Fragment() {
    private var bindingInstance: FragmentTravelInfoBinding? = null
    private val binding get() = bindingInstance!!

    private val navigationArguments by navArgs<TravelInfoFragmentArgs>()

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
            title = navigationArguments.travelInfo?.indicator
            info = Html.fromHtml(
                navigationArguments.travelInfo?.restrictions,
                Html.FROM_HTML_MODE_LEGACY
            ).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}