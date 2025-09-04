package com.qrcraft.scan.domain

import com.google.common.truth.Truth.assertThat
import com.qrcraft.scan.domain.QrCodeType.*
import org.junit.Before
import org.junit.Test

class QrTypeDetectorTest {

    private lateinit var qrTypeDetector: QrCodeTypeDetector

    @Before
    fun setUp() {
        qrTypeDetector = QrCodeTypeDetector()
    }

    @Test
    fun `is Link 1` () {
        val content = "http://https://pl-coding.mymemberspot.io"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(Link)
    }

    @Test
    fun `is Link 2` () {
        val content = "https://www.google.com/maps"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(Link)
    }

    @Test
    fun `is Contact 1` () {
        val content = "BEGIN:VCARD\nVERSION:3.0\nFN:John Doe\nTEL:+1 123 456 7890\nEMAIL:john.doe1990@gmail.com\nEND:VCARD"
        val result = qrTypeDetector.getQrCodeType(content)
        val expected = Contact(
            name = "John Doe",
            email = "john.doe1990@gmail.com",
            phone = "+1 123 456 7890"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Contact 2` () {
        val content = "BEGIN:VCARD\nVERSION:3.0\nN:Olivia Schmidt\nTEL:+1 (555) 284-7390\nEMAIL:olivia.schmidt@example.com\nEND:VCARD"
        val result = qrTypeDetector.getQrCodeType(content)
        val expected = Contact(
            name = "Olivia Schmidt",
            email = "olivia.schmidt@example.com",
            phone = "+1 (555) 284-7390"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Phone Number starting with + 1 ` () {
        val content = "tel:+49 170 1234567"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(PhoneNumber("+49 170 1234567"))
    }

    @Test
    fun `is Phone Number starting with + 2` () {
        val content = "+49 170 1234567"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(PhoneNumber("+49 170 1234567"))
    }

    @Test
    fun `is Phone Number not starting with + 1` () {
        val content = "tel:1 (555) 123-4567"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(PhoneNumber("1 (555) 123-4567"))
    }

    @Test
    fun `is Phone Number not starting with + 2` () {
        val content = "1 (555) 123-4567"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(PhoneNumber("1 (555) 123-4567"))
    }

    @Test
    fun `is Geolocation 1` () {
        val content = "48.85852536332933, 2.294459839289565"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(Geolocation(latitude = "48.85852536332933", longitude = "2.294459839289565"))
    }

    @Test
    fun `is Geolocation 2` () {
        val content = "48.85852536332933,2.294459839289565"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(Geolocation(latitude = "48.85852536332933", longitude = "2.294459839289565"))
    }

    @Test
    fun `is Geolocation 3` () {
        val content = "geo:50.4501, 30.5234"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(Geolocation(latitude = "50.4501", longitude = "30.5234"))
    }

    @Test
    fun `is Geolocation 4` () {
        val content = "geo:50.4501,30.5234"
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(Geolocation(latitude = "50.4501", longitude = "30.5234"))
    }

    @Test
    fun `is Wi-Fi 1` () {
        val content = "WIFI:T:WPA2;S:wifi-5G;P:qwerty@123;;"
        val result = qrTypeDetector.getQrCodeType(content)
        val expected = Wifi(
            ssid = "wifi-5G",
            password = "qwerty@123",
            encryption = "WPA2"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Wi-Fi 2` () {
        val content = "WIFI:S:DevHub_WiFi;T:WPA;P:QrCraft2025;H:false;;"
        val result = qrTypeDetector.getQrCodeType(content)
        val expected = Wifi(
            ssid = "DevHub_WiFi",
            password = "QrCraft2025",
            encryption = "WPA"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Text` () {
        val content = "Hello, this is a simple note without any special format."
        val result = qrTypeDetector.getQrCodeType(content)
        assertThat(result).isEqualTo(Text)
    }
}