package com.qrcraft.scan.domain

import com.google.common.truth.Truth.assertThat
import com.qrcraft.scan.domain.QrType.*
import org.junit.Before
import org.junit.Test

class QrTypeDetectorTest {

    private lateinit var qrTypeDetector: QrTypeDetector

    @Before
    fun setUp() {
        qrTypeDetector = QrTypeDetector()
    }

    @Test
    fun `is Link 1` () {
        val content = "http://www.google.com/maps"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Link(rawContent = content))
    }

    @Test
    fun `is Link 2` () {
        val content = "https://www.google.com/maps"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Link(rawContent = content))
    }

    @Test
    fun `is Contact` () {
        val content = "BEGIN:VCARD\nVERSION:3.0\nFN:John Doe\nTEL:+1 123 456 7890\nEMAIL:john.doe1990@gmail.com\nEND:VCARD"
        val result = qrTypeDetector.getQrType(content)
        val expected = Contact(
            rawContent = "BEGIN:VCARD\nVERSION:3.0\nFN:John Doe\nTEL:+1 123 456 7890\nEMAIL:john.doe1990@gmail.com\nEND:VCARD",
            name = "John Doe",
            email = "john.doe1990@gmail.com",
            phone = "+1 123 456 7890"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Phone Number starting with +` () {
        val content = "+1 123 456 7890"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(PhoneNumber(rawContent = content))
    }

    @Test
    fun `is Phone Number not starting with +` () {
        val content = "1 (555) 123-4567"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(PhoneNumber(rawContent = content))
    }

    @Test
    fun `is Geolocation` () {
        val content = "48.85852536332933, 2.294459839289565"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Geolocation(rawContent = content))
    }

    @Test
    fun `is Wi-Fi` () {
        val content = "WIFI:T:WPA2;S:wifi-5G;P:qwerty@123;;"
        val result = qrTypeDetector.getQrType(content)
        val expected = Wifi(
            rawContent = content,
            ssid = "wifi-5G",
            password = "qwerty@123",
            encryption = "WPA2"
        )
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `is Text` () {
        val content = "Hello, this is a simple note without any special format."
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Text(rawContent = content))
    }
}