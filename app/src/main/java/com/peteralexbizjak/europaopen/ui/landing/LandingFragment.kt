package com.peteralexbizjak.europaopen.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.databinding.FragmentLandingBinding
import com.peteralexbizjak.europaopen.models.GenericResponse
import com.peteralexbizjak.europaopen.utils.extensions.launchURL
import com.peteralexbizjak.europaopen.utils.extensions.sendEmail
import org.koin.androidx.viewmodel.ext.android.viewModel

class LandingFragment : Fragment() {
    private var bindingInstance: FragmentLandingBinding? = null
    private val binding get() = bindingInstance!!

    private val landingViewModel by viewModel<LandingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        landingViewModel.requestData()
    }

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

        binding.fragmentLandingContent.visibility = View.GONE
        landingViewModel.countries.observe(viewLifecycleOwner) {
            when (it) {
                is GenericResponse.Success -> {
                    binding.apply {
                        fragmentLandingContent.visibility = View.VISIBLE
                        fragmentLandingProgressBar.visibility = View.GONE
                    }

                    // On click listeners
                    binding.apply {
                        fragmentLandingRequestTravelInfo.setOnClickListener { _ ->
                            binding.root.findNavController()
                                .navigate(
                                    LandingFragmentDirections.actionLandingFragmentToTravelFragment(
                                        it.data.toTypedArray()
                                    )
                                )
                        }

                        fragmentLandingObtainData.setOnClickListener { _ ->
                            binding.root.findNavController()
                                .navigate(
                                    LandingFragmentDirections.actionLandingFragmentToStatisticsFragment(
                                        it.data
                                            .filter { model -> model.direction == "both" }
                                            .toTypedArray()
                                    )
                                )
                        }

                        fragmentLandingCode.setOnClickListener { view ->
                            launchURL("https://github.com/sunderee/EuropaOpen", view)
                        }
                        fragmentLandingSubmitFeedback.setOnClickListener { view ->
                            sendEmail(
                                "Feedback on Europa Open",
                                arrayOf(
                                    "peteralex.developer@gmail.com",
                                    "info.c2studios@gmail.com"
                                ),
                                "We are always looking forward to hearing from you.",
                                view
                            )
                        }
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