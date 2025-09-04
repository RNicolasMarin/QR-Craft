package com.qrcraft.create.presentation.create_qr

import androidx.compose.ui.graphics.Color
import com.qrcraft.R
import com.qrcraft.core.domain.QrCodeTypeId.CONTACT_ID
import com.qrcraft.core.domain.QrCodeTypeId.GEOLOCATION_ID
import com.qrcraft.core.domain.QrCodeTypeId.LINK_ID
import com.qrcraft.core.domain.QrCodeTypeId.PHONE_NUMBER_ID
import com.qrcraft.core.domain.QrCodeTypeId.TEXT_ID
import com.qrcraft.core.domain.QrCodeTypeId.WIFI_ID
import com.qrcraft.core.presentation.designsystem.*

enum class QrTypeUI(val iconRes: Int, val iconBackColor: Color, val iconColor: Color, val textRes: Int, val qrCodeTypeId: Int) {
    TEXT(R.drawable.create_qr_ic_text, TextBg, Text, R.string.qr_type_text, TEXT_ID),
    LINK(R.drawable.create_qr_ic_link, LinkBg, Link, R.string.qr_type_link, LINK_ID),
    CONTACT(R.drawable.create_qr_ic_contact, ContactBg, Contact,R.string.qr_type_contact, CONTACT_ID),
    PHONE_NUMBER(R.drawable.create_qr_ic_phone_number, PhoneNumberBg, PhoneNumber, R.string.qr_type_phone_number, PHONE_NUMBER_ID),
    GEOLOCATION(R.drawable.create_qr_ic_geolocation, GeolocationBg, Geolocation, R.string.qr_type_geolocation, GEOLOCATION_ID),
    WIFI(R.drawable.create_qr_ic_wifi, WifiBg, Wifi, R.string.qr_type_wifi, WIFI_ID),
}