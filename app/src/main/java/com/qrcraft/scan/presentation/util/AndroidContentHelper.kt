package com.qrcraft.scan.presentation.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.qrcraft.R
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

suspend fun Context.tryToDownloadQrCodeAsImage(bitmap: Bitmap): Boolean = withContext(Dispatchers.IO) {
    val fileName = "QrCraft_Image_${System.currentTimeMillis()}.png"
    val resolver = contentResolver
    val mimeType = "image/png"

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

            if (uri == null) {
                return@withContext false
            }

            return@withContext resolver.openOutputStream(uri)?.use { outStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            } ?: run {
                false
            }
        }
        return@withContext false
    } catch (e: Exception) {
        return@withContext false
    }
}