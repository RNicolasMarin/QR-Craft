package com.qrcraft.create.presentation.data_entry

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qrcraft.core.presentation.components.QRCraftTopBar
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.create.presentation.create_qr.QrTypeUI
import com.qrcraft.create.presentation.create_qr.QrTypeUI.CONTACT
import com.qrcraft.create.presentation.create_qr.QrTypeUI.GEOLOCATION
import com.qrcraft.create.presentation.create_qr.QrTypeUI.LINK
import com.qrcraft.create.presentation.create_qr.QrTypeUI.PHONE_NUMBER
import com.qrcraft.create.presentation.create_qr.QrTypeUI.TEXT
import com.qrcraft.create.presentation.create_qr.QrTypeUI.WIFI
import com.qrcraft.create.presentation.data_entry.DataEntryAction.GoBackToCreateQr

@Composable
fun DataEntryScreenRoot(
    qrTypeOrdinal: Int,
    onBackToCreateQr: () -> Unit,
) {
    DataEntryScreen(
        qrTypeUI = QrTypeUI.entries.getOrNull(qrTypeOrdinal) ?: TEXT,
        onAction = { action ->
            when (action) {
                GoBackToCreateQr -> onBackToCreateQr()
            }
        }
    )
}

@Composable
fun DataEntryScreen(
    qrTypeUI: QrTypeUI,
    onAction: (DataEntryAction) -> Unit,
    dimens: DimensTopBar = MaterialTheme.dimen.topBar
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(start = dimens.paddingStart, end = dimens.paddingEnd, bottom = 16.dp)
    ) {
        QRCraftTopBar(
            color = MaterialTheme.colorScheme.onSurface,
            titleRes = qrTypeUI.textRes,
            onBackClicked = { onAction(GoBackToCreateQr) }
        )
    }

}

@MultiDevicePreview
@Composable
private fun DataEntryScreenPreviewText() {
    QRCraftTheme {
        DataEntryScreen(
            qrTypeUI = TEXT,
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
            onAction = {}
        )
    }
}