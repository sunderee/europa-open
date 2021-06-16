package com.peteralexbizjak.europaopen.ui

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.peteralexbizjak.europaopen.databinding.FragmentRuleSingleBinding

class RuleSingleFragment : Fragment() {
    private var bindingInstance: FragmentRuleSingleBinding? = null
    private val binding get() = bindingInstance!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindingInstance = FragmentRuleSingleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            binding.title = it.getString("title").toString()
            binding.contents = Html.fromHtml(
                it.getString("contents").toString(),
                Html.FROM_HTML_MODE_COMPACT
            ).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingInstance = null
    }
}