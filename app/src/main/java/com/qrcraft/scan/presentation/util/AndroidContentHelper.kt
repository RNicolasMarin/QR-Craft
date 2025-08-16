package com.qrcraft.scan.presentation.util

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.qrcraft.R
import androidx.core.net.toUri

fun Context.shareContent(content: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, content)
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share via")
    startActivity(shareIntent)
}

fun Context.copyContent(clipboardManager: ClipboardManager, content: String) {
    clipboardManager.setText(AnnotatedString(content))
    Toast.makeText(
        this,
        getString(R.string.scan_result_copied),
        Toast.LENGTH_LONG
    ).show()
}

fun Context.opeLink(link: String) {
    val intent = Intent(Intent.ACTION_VIEW, link.toUri())
    startActivity(intent)
}