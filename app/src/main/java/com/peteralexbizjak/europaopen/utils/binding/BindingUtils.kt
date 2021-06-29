package com.peteralexbizjak.europaopen.utils.binding

import android.text.util.Linkify
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter

internal object BindingUtils {
    @BindingAdapter(value = ["loadHtml"])
    @JvmStatic
    fun loadHtml(textView: TextView, content: String?) {
        if (!content.isNullOrEmpty()) {
            textView.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_COMPACT)
            Linkify.addLinks(textView, Linkify.ALL)
        }
    }
}