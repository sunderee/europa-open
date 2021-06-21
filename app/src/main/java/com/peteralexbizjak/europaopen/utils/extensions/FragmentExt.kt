package com.peteralexbizjak.europaopen.utils.extensions

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

@Throws(ActivityNotFoundException::class)
internal fun Fragment.launchURL(url: String, view: View) {
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

internal fun Fragment.sendEmail(
    subject: String,
    receivers: Array<String>,
    body: String,
    view: View
) {
    if (receivers.all { it.isEmailValid() }) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, receivers)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REQUIRE_DEFAULT
            } else {
                Intent.FLAG_ACTIVITY_NEW_TASK
            }
        }
        context?.startActivity(emailIntent)
    } else {
        Snackbar.make(view, "Couldn't launch an email client", Snackbar.LENGTH_SHORT).show()
    }
}

private fun String.isUrlValid(): Boolean =
    URLUtil.isValidUrl(this)

private fun String.isEmailValid(): Boolean =
    android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()