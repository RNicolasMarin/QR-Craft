package com.qrcraft.create.domain

import com.google.common.truth.Truth.assertThat
import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.QrCodeType.*
import org.junit.Before
import org.junit.Test

class RawContentGeneratorTest {

    private lateinit var rawContentGenerator: RawContentGenerator

    @Before
    fun setUp() {
        rawContentGenerator = RawContentGenerator()
    }

    @Test
    fun `valid Link`() {
        val data = "https://pl-coding.mymemberspot.io"
        val content = QrCode(
            type = Link,
            rawContent = data
        )
        val result = rawContentGenerator.createContent(content)
        assertThat(result).isEqualTo(data)
    }

    @Test
    fun `valid Contact`() {
        val data = "BEGIN:VCARD\nVERSION:3.0\nN:Olivia Schmidt\nTEL:+1 (555) 284-7390\nEMAIL:olivia.schmidt@example.com\nEND:VCARD"
        val content = QrCode(
            type = Contact(
                name = "Olivia Schmidt",
                email = "olivia.schmidt@example.com",
                phone = "+1 (555) 284-7390"
            ),
            rawContent = data
        )
        val result = rawContentGenerator.createContent(content)
        assertThat(result).isEqualTo(data)
    }

    @Test
    fun `valid Phone Number`() {
        val data = "tel:1 (555) 123-4567"
        val content = QrCode(
            type = PhoneNumber("1 (555) 123-4567"),
            rawContent = data
        )
        val result = rawContentGenerator.createContent(content)
        assertThat(result).isEqualTo(data)
    }

    @Test
    fun `valid Geolocation`() {
        val data = "geo:50.4501,30.5234"
        val content = QrCode(
            type = Geolocation(
                latitude = "50.4501",
                longitude = "30.5234",
            ),
            rawContent = data
        )
        val result = rawContentGenerator.createContent(content)
        assertThat(result).isEqualTo(data)
    }

    @Test
    fun `valid Wifi`() {
        val data = "WIFI:T:WPA;S:DevHub_WiFi;P:QrCraft2025;;"
        val content = QrCode(
            type = Wifi(
                ssid = "DevHub_WiFi",
                password = "QrCraft2025",
                encryption = "WPA"
            ),
            rawContent = data
        )
        val result = rawContentGenerator.createContent(content)
        assertThat(result).isEqualTo(data)
    }

    @Test
    fun `valid Text`() {
        val data = "Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum justo metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium. Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium."
        val content = QrCode(
            type = Text,
            rawContent = data
        )
        val result = rawContentGenerator.createContent(content)
        assertThat(result).isEqualTo(data)
    }

}