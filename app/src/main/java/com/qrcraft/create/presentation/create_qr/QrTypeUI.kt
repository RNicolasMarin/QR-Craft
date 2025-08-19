package com.qrcraft.create.presentation.create_qr

import androidx.compose.ui.graphics.Color
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.*

enum class QrTypeUI(val iconRes: Int, val iconBackColor: Color, val iconColor: Color, val textRes: Int) {
    TEXT(R.drawable.create_qr_ic_text, TextBg, Text, R.string.qr_type_text),
    LINK(R.drawable.create_qr_ic_link, LinkBg, Link, R.string.qr_type_link),
    CONTACT(R.drawable.create_qr_ic_contact, ContactBg, Contact,R.string.qr_type_contact),
    PHONE_NUMBER(R.drawable.create_qr_ic_phone_number, PhoneNumberBg, PhoneNumber, R.string.qr_type_phone_number),
    GEOLOCATION(R.drawable.create_qr_ic_geolocation, GeolocationBg, Geolocation, R.string.qr_type_geolocation),
    WIFI(R.drawable.create_qr_ic_wifi, WifiBg, Wifi, R.string.qr_type_wifi),
}