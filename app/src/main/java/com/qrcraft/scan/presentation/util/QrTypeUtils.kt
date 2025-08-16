package com.qrcraft.scan.presentation.util

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.qrcraft.R
import com.qrcraft.scan.domain.QrType
import com.qrcraft.scan.domain.QrType.*
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set

fun QrType.getStringRes(): Int {
    return when (this) {
        is Link -> R.string.qr_type_link
        is Contact -> R.string.qr_type_contact
        is PhoneNumber -> R.string.qr_type_phone_number
        is Geolocation -> R.string.qr_type_geolocation
        is Wifi -> R.string.qr_type_wifi
        is Text -> R.string.qr_type_text
    }
}

@Composable
fun QrType.getFormattedContent(): String {
    return when (this) {
        is Text, is Link, is PhoneNumber, is Geolocation -> rawContent
        is Contact -> "$name\n$email\n$phone"

        is Wifi -> {
            val encryptionValue = encryption
            val ssidValue = ssid
            val passwordValue = password

            if (encryptionValue != null && ssidValue != null && passwordValue != null) {
                "${stringResource(R.string.qr_type_wifi_ssid)} $ssidValue\n${stringResource(R.string.qr_type_wifi_password)} $passwordValue\n${stringResource(R.string.qr_type_wifi_encryption)} $encryptionValue"
            } else {
                rawContent
            }
        }
    }
}

fun generateQrCode(content: String, size: Int = 512): Bitmap {
    val bitMatrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
    val bitmap = createBitmap(size, size, Bitmap.Config.RGB_565)

    for (x in 0 until size) {
        for (y in 0 until size) {
            bitmap[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
        }
    }
    return bitmap
}