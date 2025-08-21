package com.qrcraft.create.presentation.data_entry

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.QRCraftTopBar
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.OnSurfaceDisabled
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.SurfaceHigher
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.create.presentation.create_qr.QrTypeUI
import com.qrcraft.create.presentation.create_qr.QrTypeUI.CONTACT
import com.qrcraft.create.presentation.create_qr.QrTypeUI.GEOLOCATION
import com.qrcraft.create.presentation.create_qr.QrTypeUI.LINK
import com.qrcraft.create.presentation.create_qr.QrTypeUI.PHONE_NUMBER
import com.qrcraft.create.presentation.create_qr.QrTypeUI.TEXT
import com.qrcraft.create.presentation.create_qr.QrTypeUI.WIFI
import com.qrcraft.create.presentation.data_entry.DataEntryAction.*
import com.qrcraft.create.presentation.data_entry.DataEntryAction.UpdateQrContent.*
import com.qrcraft.scan.domain.QrType
import com.qrcraft.scan.domain.QrType.*
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DataEntryScreenRoot(
    qrTypeOrdinal: Int,
    onBackToCreateQr: () -> Unit,
    onGoToPreview: () -> Unit,
    viewModel: DataEntryViewModel = koinViewModel()
) {

    LaunchedEffect(true) {
        viewModel.onAction(SetQrType(QrTypeUI.entries.getOrNull(qrTypeOrdinal) ?: TEXT))
    }

    DataEntryScreen(
        qrTypeUI = QrTypeUI.entries.getOrNull(qrTypeOrdinal) ?: TEXT,
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                GoBackToCreateQr -> onBackToCreateQr()
                GoToPreview -> onGoToPreview()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun DataEntryScreen(
    qrTypeUI: QrTypeUI,
    state: DataEntryState,
    onAction: (DataEntryAction) -> Unit,
    dimens: DimensTopBar = MaterialTheme.dimen.topBar
) {
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(start = dimens.paddingStart, end = dimens.paddingEnd, bottom = 16.dp)
            .verticalScroll(scrollState),
    ) {
        QRCraftTopBar(
            color = MaterialTheme.colorScheme.onSurface,
            titleRes = qrTypeUI.textRes,
            onBackClicked = { onAction(GoBackToCreateQr) }
        )
        DataEntryScreenContent(
            state = state,
            onAction = onAction
        )
    }
}

@Composable
fun DataEntryScreenContent(
    state: DataEntryState,
    onAction: (DataEntryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(480.dp)
            .background(SurfaceHigher, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        DataEntryScreenContentFields(
            state = state,
            onAction = onAction
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                disabledContentColor = OnSurfaceDisabled
            ),
            contentPadding = PaddingValues(12.dp),
            enabled = state.canGenerate,
            onClick = { onAction(GoToPreview) }
        ) {
            Text(
                text = stringResource(R.string.data_entry_generate),
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DataEntryScreenContentFields(
    state: DataEntryState,
    onAction: (DataEntryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when (state.qrType) {
        is Text -> {
            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_text_placeholder,
                onValueChange = { onAction(UpdateText(it)) },
                value = state.qrType.rawContent,
                modifier = modifier.fillMaxWidth(),
                singleLine = false
            )
        }
        is Link -> {
            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_link_placeholder,
                onValueChange = { onAction(UpdateLink(it)) },
                value = state.qrType.rawContent,
                modifier = modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Uri
            )
        }
        is Contact -> {
            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_contact_name_placeholder,
                onValueChange = { onAction(UpdateContactName(it)) },
                value = state.qrType.name,
                modifier = modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_contact_email_placeholder,
                onValueChange = { onAction(UpdateContactEmail(it)) },
                value = state.qrType.email,
                modifier = modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(8.dp))

            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_contact_phone_number_placeholder,
                onValueChange = { onAction(UpdateContactPhone(it)) },
                value = state.qrType.phone,
                modifier = modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone
            )
        }
        is PhoneNumber -> {
            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_phone_number_placeholder,
                onValueChange = { onAction(UpdatePhoneNumber(it)) },
                value = state.qrType.rawContent,
                modifier = modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Phone
            )
        }
        is Geolocation -> {
            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_geolocation_latitude_placeholder,
                onValueChange = { onAction(UpdateGeolocationLatitude(it)) },
                value = state.qrType.latitude,
                modifier = modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Decimal
            )

            Spacer(modifier = Modifier.height(8.dp))

            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_geolocation_longitude_placeholder,
                onValueChange = { onAction(UpdateGeolocationLongitude(it)) },
                value = state.qrType.longitude,
                modifier = modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Decimal
            )
        }
        is Wifi -> {
            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_wifi_ssid_placeholder,
                onValueChange = { onAction(UpdateWifiSsid(it)) },
                value = state.qrType.ssid,
                modifier = modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_wifi_password_placeholder,
                onValueChange = { onAction(UpdateWifiPassword(it)) },
                value = state.qrType.password,
                modifier = modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            DataEntryScreenContentField(
                placeholderRes = R.string.data_entry_wifi_encryption_type_placeholder,
                onValueChange = { onAction(UpdateWifiEncryption(it)) },
                value = state.qrType.encryption,
                modifier = modifier.fillMaxWidth()
            )
        }
        null -> Unit
    }
}

@Composable
fun DataEntryScreenContentField(
    @StringRes placeholderRes: Int,
    onValueChange: (String) -> Unit,
    value: String?,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        value = value ?: "",
        onValueChange = onValueChange,
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            //underline
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        placeholder = {
            Text(
                text = stringResource(placeholderRes),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}

@MultiDevicePreview
@Composable
private fun DataEntryScreenPreviewText() {
    QRCraftTheme {
        DataEntryScreen(
            qrTypeUI = TEXT,
            state = DataEntryState(
                qrType = QrType.Text("Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum justo metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium. Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium.")
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun DataEntryScreenPreviewLink() {
    QRCraftTheme {
        DataEntryScreen(
            qrTypeUI = LINK,
            state = DataEntryState(
                qrType = Link("http://https://pl-coding.mymemberspot.io")
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun DataEntryScreenPreviewContact() {
    QRCraftTheme {
        DataEntryScreen(
            qrTypeUI = CONTACT,
            state = DataEntryState(
                qrType = Contact(
                    rawContent = "",
                    name = "Olivia Schmidt",
                    email = "olivia.schmidt@example.com",
                    phone = "+1 (555) 284-7390"
                )
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun DataEntryScreenPreviewPhoneNumber() {
    QRCraftTheme {
        DataEntryScreen(
            qrTypeUI = PHONE_NUMBER,
            state = DataEntryState(
                qrType = PhoneNumber(
                    rawContent = "+49 170 1234567"
                )
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun DataEntryScreenPreviewGeolocation() {
    QRCraftTheme {
        DataEntryScreen(
            qrTypeUI = GEOLOCATION,
            state = DataEntryState(
                qrType = Geolocation(
                    rawContent = "",
                    latitude = "50.4501",
                    longitude = "30.5234",
                )
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun DataEntryScreenPreviewWifi() {
    QRCraftTheme {
        DataEntryScreen(
            qrTypeUI = WIFI,
            state = DataEntryState(
                qrType = Wifi(
                    rawContent = "",
                    ssid = "DevHub_WiFi",
                    password = "QrCraft2025",
                    encryption = "WPA"
                )
            ),
            onAction = {}
        )
    }
}