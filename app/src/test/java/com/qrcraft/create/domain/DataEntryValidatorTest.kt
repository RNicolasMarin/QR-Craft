package com.qrcraft.create.domain

import com.google.common.truth.Truth.assertThat
import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.QrCodeType.*
import org.junit.Before
import org.junit.Test

class DataEntryValidatorTest {

    private lateinit var dataEntryValidator: DataEntryValidator

    @Before
    fun setUp() {
        dataEntryValidator = DataEntryValidator()
    }

    @Test
    fun `valid Link 1`() {
        val content = QrCode(
            rawContent = "http://https://pl-coding.mymemberspot.io",
            type = Link
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isTrue()
    }

    @Test
    fun `valid Link 2`() {
        val content = QrCode(
            rawContent = "https://www.google.com/maps",
            type = Link
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Link`() {
        val content = QrCode(
            rawContent = "htt://www.google.com/maps",
            type = Link
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `valid Contact`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = "Olivia Schmidt",
                email = "olivia.schmidt@example.com",
                phone = "+1 (555) 284-7390"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Contact no name`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = "",
                email = "olivia.schmidt@example.com",
                phone = "+1 (555) 284-7390"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact null name`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = null,
                email = "olivia.schmidt@example.com",
                phone = "+1 (555) 284-7390"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact no email`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = "Olivia Schmidt",
                email = "",
                phone = "+1 (555) 284-7390"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact null email`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = "Olivia Schmidt",
                email = null,
                phone = "+1 (555) 284-7390"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact no phone`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = "Olivia Schmidt",
                email = "olivia.schmidt@example.com",
                phone = ""
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact null phone`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = "Olivia Schmidt",
                email = "olivia.schmidt@example.com",
                phone = null
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact email`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = "Olivia Schmidt",
                email = "olivia.schmidt.example.com",
                phone = "+1 (555) 284-7390"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact phone`() {
        val content = QrCode(
            rawContent = "",
            type = Contact(
                name = "Olivia Schmidt",
                email = "olivia.schmidt@example.com",
                phone = "+1 (555) a 284-7390"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `valid Phone Number 1`() {
        val content = QrCode(
            rawContent = "+49 170 1234567",
            type = PhoneNumber
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isTrue()
    }

    @Test
    fun `valid Phone Number 2`() {
        val content = QrCode(
            rawContent = "49 170 1234567",
            type = PhoneNumber
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Phone Number`() {
        val content = QrCode(
            rawContent = "+49 a 170 1234567",
            type = PhoneNumber
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 no latitude and longitude`() {
        val content = QrCode(
            rawContent = "",
            type = Geolocation(
                latitude = "",
                longitude = ""
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 null latitude and longitude`() {
        val content = QrCode(
            rawContent = "",
            type = Geolocation(
                latitude = null,
                longitude = null
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 no latitude`() {
        val content = QrCode(
            rawContent = "",
            type = Geolocation(
                latitude = "",
                longitude = "30.5234"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 no longitude`() {
        val content = QrCode(
            rawContent = "",
            type = Geolocation(
                latitude = "50.4501",
                longitude = ""
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 null latitude`() {
        val content = QrCode(
            rawContent = "",
            type = Geolocation(
                latitude = null,
                longitude = "30.5234"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 null longitude`() {
        val content = QrCode(
            rawContent = "",
            type = Geolocation(
                latitude = "50.4501",
                longitude = null
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `valid Geolocation`() {
        val content = QrCode(
            rawContent = "",
            type = Geolocation(
                latitude = "50.4501",
                longitude = "30.5234"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Wifi no ssid, password and encryption`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = "",
                password = "",
                encryption = ""
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi null ssid, password and encryption`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = null,
                password = null,
                encryption = null
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi no ssid`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = "",
                password = "QrCraft2025",
                encryption = "WPA"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi null ssid`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = null,
                password = "QrCraft2025",
                encryption = "WPA"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi no password`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = "DevHub_WiFi",
                password = "",
                encryption = "WPA"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi null password`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = "DevHub_WiFi",
                password = null,
                encryption = "WPA"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi no encryption`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = "DevHub_WiFi",
                password = "QrCraft2025",
                encryption = ""
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi null encryption`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = "DevHub_WiFi",
                password = "QrCraft2025",
                encryption = null
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }

    @Test
    fun `valid Wifi`() {
        val content = QrCode(
            rawContent = "",
            type = Wifi(
                ssid = "DevHub_WiFi",
                password = "QrCraft2025",
                encryption = "WPA"
            )
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isTrue()
    }

    @Test
    fun `valid Text`() {
        val content = QrCode(
            rawContent = "Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum justo metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium. Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium.",
            type = Text
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Text`() {
        val content = QrCode(
            rawContent = "",
            type = Text
        )
        val result = dataEntryValidator.isValidContent(content)
        assertThat(result).isFalse()
    }
}