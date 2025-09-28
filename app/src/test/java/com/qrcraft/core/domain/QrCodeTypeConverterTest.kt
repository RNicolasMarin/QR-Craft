package com.qrcraft.core.domain

import com.google.common.truth.Truth.assertThat
import com.qrcraft.core.domain.QrCodeTypeId.CONTACT_ID
import com.qrcraft.core.domain.QrCodeTypeId.GEOLOCATION_ID
import com.qrcraft.core.domain.QrCodeTypeId.LINK_ID
import com.qrcraft.core.domain.QrCodeTypeId.PHONE_NUMBER_ID
import com.qrcraft.core.domain.QrCodeTypeId.TEXT_ID
import com.qrcraft.core.domain.QrCodeTypeId.WIFI_ID
import com.qrcraft.scan.domain.QrCodeType.*
import org.junit.Before
import org.junit.Test

class QrCodeTypeConverterTest {

    private lateinit var qrCodeTypeConverter: QrCodeTypeConverter

    @Before
    fun setUp() {
        qrCodeTypeConverter = QrCodeTypeConverter()
    }

    @Test
    fun `is Link`() {
        val result = qrCodeTypeConverter.convertToType(LINK_ID, "https://pl-coding.mymemberspot.io")
        assertThat(result).isEqualTo(Link)
    }

    @Test
    fun `is Contact Valid`() {
        val result = qrCodeTypeConverter.convertToType(CONTACT_ID, "BEGIN:VCARD\nVERSION:3.0\nN:Olivia Schmidt\nTEL:+1 (555) 284-7390\nEMAIL:olivia.schmidt@example.com\nEND:VCARD")
        val expected = Contact(
            name = "Olivia Schmidt",
            email = "olivia.schmidt@example.com",
            phone = "+1 (555) 284-7390"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Contact invalid`() {
        val result = qrCodeTypeConverter.convertToType(CONTACT_ID, "BEGIN:VCRD\nVERSION:3.0\nN:Olivia Schmidt\nTEL:+1 (555) 284-7390\nEMAIL:olivia.schmidt@example.com\nEND:VCARD")
        assertThat(result).isEqualTo(Text)
    }

    @Test
    fun `is Phone Number Valid`() {
        val result = qrCodeTypeConverter.convertToType(PHONE_NUMBER_ID, "tel:1 (555) 123-4567")
        val expected = PhoneNumber("1 (555) 123-4567")
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Phone Number invalid`() {
        val result = qrCodeTypeConverter.convertToType(PHONE_NUMBER_ID, "atel:1 (555) 123-4567")
        assertThat(result).isEqualTo(Text)
    }

    @Test
    fun `is Geolocation Valid`() {
        val result = qrCodeTypeConverter.convertToType(GEOLOCATION_ID, "50.4501,30.5234")
        val expected = Geolocation(
            latitude = "50.4501",
            longitude = "30.5234",
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Geolocation invalid`() {
        val result = qrCodeTypeConverter.convertToType(GEOLOCATION_ID, "a50.4501,30.5234")
        assertThat(result).isEqualTo(Text)
    }

    @Test
    fun `is Wifi Valid`() {
        val result = qrCodeTypeConverter.convertToType(WIFI_ID, "WIFI:S:DevHub_WiFi;T:WPA;P:QrCraft2025;H:false;;")
        val expected = Wifi(
            ssid = "DevHub_WiFi",
            password = "QrCraft2025",
            encryption = "WPA"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Wifi invalid`() {
        val result = qrCodeTypeConverter.convertToType(WIFI_ID, "aWIFI:S:DevHub_WiFi;T:WPA;P:QrCraft2025;H:false;;")
        assertThat(result).isEqualTo(Text)
    }

    @Test
    fun `is Text Valid`() {
        val result = qrCodeTypeConverter.convertToType(TEXT_ID, "Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum justo metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium. Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium.")
        assertThat(result).isEqualTo(Text)
    }

}