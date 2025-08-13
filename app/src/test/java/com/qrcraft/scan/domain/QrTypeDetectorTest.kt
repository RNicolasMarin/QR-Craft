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
        val content = "http://www.example.com"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Link(rawContent = content))
    }

    @Test
    fun `is Link 2` () {
        val content = "https://www.example.com"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Link(rawContent = content))
    }

    @Test
    fun `is Contact` () {
        val content = "BEGIN:VCARD\nVERSION:3.0\nFN:John Doe\nTEL:+123456789\nEMAIL:johndoe@example.com\nEND:VCARD"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Contact(rawContent = content))
    }

    @Test
    fun `is Phone Number starting with +` () {
        val content = "+1 (555) 123-4567"
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
        val content = "37.7749, -122.4194"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Geolocation(rawContent = content))
    }

    @Test
    fun `is Wi-Fi` () {
        val content = "WIFI:S:MyNetwork;T:WPA;P:mypassword;;"
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Wifi(rawContent = content))
    }

    @Test
    fun `is Text` () {
        val content = "Hello, this is a simple note without any special format."
        val result = qrTypeDetector.getQrType(content)
        assertThat(result).isEqualTo(Text(rawContent = content))
    }
}