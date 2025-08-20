package com.qrcraft.create.domain

import com.google.common.truth.Truth.assertThat
import com.qrcraft.scan.domain.QrType
import com.qrcraft.scan.domain.QrType.Contact
import com.qrcraft.scan.domain.QrType.Geolocation
import com.qrcraft.scan.domain.QrType.Link
import com.qrcraft.scan.domain.QrType.PhoneNumber
import com.qrcraft.scan.domain.QrType.Wifi
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
        val content = "http://https://pl-coding.mymemberspot.io"
        val result = dataEntryValidator.isValidContent(Link(content))
        assertThat(result).isTrue()
    }

    @Test
    fun `valid Link 2`() {
        val content = "https://www.google.com/maps"
        val result = dataEntryValidator.isValidContent(Link(content))
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Link`() {
        val content = "htt://www.google.com/maps"
        val result = dataEntryValidator.isValidContent(Link(content))
        assertThat(result).isFalse()
    }

    @Test
    fun `valid Contact`() {
        val type = Contact(
            rawContent = "",
            name = "Olivia Schmidt",
            email = "olivia.schmidt@example.com",
            phone = "+1 (555) 284-7390"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Contact no name`() {
        val type = Contact(
            rawContent = "",
            name = "",
            email = "olivia.schmidt@example.com",
            phone = "+1 (555) 284-7390"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact null name`() {
        val type = Contact(
            rawContent = "",
            name = null,
            email = "olivia.schmidt@example.com",
            phone = "+1 (555) 284-7390"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact no email`() {
        val type = Contact(
            rawContent = "",
            name = "Olivia Schmidt",
            email = "",
            phone = "+1 (555) 284-7390"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact null email`() {
        val type = Contact(
            rawContent = "",
            name = "Olivia Schmidt",
            email = null,
            phone = "+1 (555) 284-7390"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact no phone`() {
        val type = Contact(
            rawContent = "",
            name = "Olivia Schmidt",
            email = "olivia.schmidt@example.com",
            phone = ""
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact null phone`() {
        val type = Contact(
            rawContent = "",
            name = "Olivia Schmidt",
            email = "olivia.schmidt@example.com",
            phone = null
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact email`() {
        val type = Contact(
            rawContent = "",
            name = "Olivia Schmidt",
            email = "olivia.schmidt.example.com",
            phone = "+1 (555) 284-7390"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Contact phone`() {
        val type = Contact(
            rawContent = "",
            name = "Olivia Schmidt",
            email = "olivia.schmidt@example.com",
            phone = "+1 (555) a 284-7390"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `valid Phone Number 1`() {
        val type = PhoneNumber(
            rawContent = "+49 170 1234567"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isTrue()
    }

    @Test
    fun `valid Phone Number 2`() {
        val type = PhoneNumber(
            rawContent = "49 170 1234567"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Phone Number`() {
        val type = PhoneNumber(
            rawContent = "+49 a 170 1234567"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 no latitude and longitude`() {
        val type = Geolocation(
            rawContent = "",
            latitude = "",
            longitude = ""
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 null latitude and longitude`() {
        val type = Geolocation(
            rawContent = "",
            latitude = null,
            longitude = null
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 no latitude`() {
        val type = Geolocation(
            rawContent = "",
            latitude = "",
            longitude = "30.5234"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 no longitude`() {
        val type = Geolocation(
            rawContent = "",
            latitude = "50.4501",
            longitude = ""
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 null latitude`() {
        val type = Geolocation(
            rawContent = "",
            latitude = null,
            longitude = "30.5234"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Geolocation 1 null longitude`() {
        val type = Geolocation(
            rawContent = "",
            latitude = "50.4501",
            longitude = null
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `valid Geolocation`() {
        val type = Geolocation(
            rawContent = "",
            latitude = "50.4501",
            longitude = "30.5234"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Wifi no ssid, password and encryption`() {
        val type = Wifi(
            rawContent = "",
            ssid = "",
            password = "",
            encryption = ""
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi null ssid, password and encryption`() {
        val type = Wifi(
            rawContent = "",
            ssid = null,
            password = null,
            encryption = null
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi no ssid`() {
        val type = Wifi(
            rawContent = "",
            ssid = "",
            password = "QrCraft2025",
            encryption = "WPA"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi null ssid`() {
        val type = Wifi(
            rawContent = "",
            ssid = null,
            password = "QrCraft2025",
            encryption = "WPA"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi no password`() {
        val type = Wifi(
            rawContent = "",
            ssid = "DevHub_WiFi",
            password = "",
            encryption = "WPA"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi null password`() {
        val type = Wifi(
            rawContent = "",
            ssid = "DevHub_WiFi",
            password = null,
            encryption = "WPA"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi no encryption`() {
        val type = Wifi(
            rawContent = "",
            ssid = "DevHub_WiFi",
            password = "QrCraft2025",
            encryption = ""
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `invalid Wifi null encryption`() {
        val type = Wifi(
            rawContent = "",
            ssid = "DevHub_WiFi",
            password = "QrCraft2025",
            encryption = null
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }

    @Test
    fun `valid Wifi`() {
        val type = Wifi(
            rawContent = "",
            ssid = "DevHub_WiFi",
            password = "QrCraft2025",
            encryption = "WPA"
        )
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isTrue()
    }

    @Test
    fun `valid Text`() {
        val type = QrType.Text("Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum justo metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium. Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium.")
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isTrue()
    }

    @Test
    fun `invalid Text`() {
        val type = QrType.Text("")
        val result = dataEntryValidator.isValidContent(type)
        assertThat(result).isFalse()
    }
}