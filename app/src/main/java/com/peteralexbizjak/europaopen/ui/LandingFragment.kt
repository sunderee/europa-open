package com.peteralexbizjak.europaopen.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.peteralexbizjak.europaopen.databinding.FragmentLandingBinding

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

        // Launch URL to the GitHub repository
        binding.fragmentLandingCode.setOnClickListener { launchURL(it) }
    }

    @Throws(ActivityNotFoundException::class)
    private fun launchURL(view: View) {
        val url = "https://github.com/sunderee/EuropaOpen"
        if (url.isUrlValid()) {
            val urlLauncherIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                addCategory(Intent.CATEGORY_BROWSABLE)
                flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_DEFAULT
                } else {
                    Intent.FLAG_ACTIVITY_NEW_TASK
                }
            }
            context?.startActivity(urlLauncherIntent)
        } else {
            Snackbar.make(view, "Invalid URL", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun String.isUrlValid(): Boolean = URLUtil.isValidUrl(this)
}