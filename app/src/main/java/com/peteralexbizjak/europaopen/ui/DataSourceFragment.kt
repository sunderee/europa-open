package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.peteralexbizjak.europaopen.databinding.FragmentDataSourceBinding
import com.peteralexbizjak.europaopen.utils.extensions.launchURL
import com.peteralexbizjak.europaopen.utils.extensions.sendEmail

class DataSourceFragment : Fragment() {
    private var bindingInstance: FragmentDataSourceBinding? = null
    private val binding get() = bindingInstance!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentDataSourceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentDataSourceToolbar.setNavigationOnClickListener {
            binding.root.findNavController().popBackStack()
        }

        // Button on click listeners
        binding.apply {
            fragmentDataSourceOfficialWebsite.setOnClickListener {
                launchURL("https://reopen.europa.eu/en", it)
            }
            fragmentDataSourceGitHub.setOnClickListener {
                launchURL("https://github.com/sunderee/EuropaOpen", it)
            }
            fragmentDataSourceContactDevs.setOnClickListener {
                sendEmail(
                    "Europa App Contact",
                    arrayOf("peteralex.developer@gmail.com", "coffee2stuff.converter@gmail.com"),
                    "Your message, feel free to edit that but leave the subject line as it is",
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