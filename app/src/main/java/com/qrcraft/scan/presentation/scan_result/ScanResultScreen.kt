package com.qrcraft.scan.presentation.scan_result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.LinkBg
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.statusBarHeight
import com.qrcraft.scan.domain.QrType
import com.qrcraft.scan.domain.QrType.*
import com.qrcraft.scan.presentation.scan_result.ScanResultAction.*
import com.qrcraft.scan.presentation.util.getFormattedContent
import com.qrcraft.scan.presentation.util.getStringRes
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScanResultScreenRoot(
    qrContent: String,
    onBackToScan: () -> Unit,
    viewModel: ScanResultViewModel = koinViewModel()
) {

    LaunchedEffect(true) {
        viewModel.onAction(SetQrContent(qrContent))
    }

    ScanResultScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                GoBackToScan -> onBackToScan()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultScreen(
    state: ScanResultState,
    onAction: (ScanResultAction) -> Unit
) {
    BackHandler {
        onAction(GoBackToScan)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
    ) {
        ScanResultTopBar(onAction = onAction)

        Spacer(modifier = Modifier.height(44.dp))

        ScanResultScannedContent(
            state = state,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ScanResultTopBar(
    onAction: (ScanResultAction) -> Unit
) {
    Spacer(
        modifier = Modifier.height(statusBarHeight())
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.arrow_left),
            tint = OnOverlay,
            contentDescription = "Back",
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = { onAction(GoBackToScan) })
        )
        Spacer(
            modifier = Modifier.width(8.dp)
        )
        Text(
            text = stringResource(R.string.scan_result),
            style = MaterialTheme.typography.titleMedium,
            color = OnOverlay,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Spacer(
            modifier = Modifier.width(24.dp)
        )
    }
}

@Composable
fun ScanResultScannedContent(
    state: ScanResultState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp,
                top = 20.dp
            )
    ) {
        state.qrType?.let {
            Text(
                text = stringResource(it.getStringRes()),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(10.dp))

            val contentModifier = when(it) {
                is QrType.Text -> Modifier.fillMaxWidth()
                is Link -> Modifier.background(LinkBg)
                else -> Modifier
            }

            Text(
                text = it.getFormattedContent(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = if (it is Text) TextAlign.Start else TextAlign.Center,
                modifier = contentModifier
            )
        }
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewText() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(
                qrType = QrType.Text("Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum justo metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium. Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium.")
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewLink() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(
                qrType = Link("https://www.google.com/maps")
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewContact() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(
                qrType = Contact(
                    rawContent = "BEGIN:VCARD\nVERSION:3.0\nFN:John Doe\nTEL:+1 123 456 7890\nEMAIL:john.doe1990@gmail.com\nEND:VCARD",
                    name = "John Doe",
                    email = "john.doe1990@gmail.com",
                    phone = "+1 123 456 7890"
                )
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewPhoneNumber() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(
                qrType = PhoneNumber(
                    rawContent = "+1 123 456 7890",
                )
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewGeolocation() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(
                qrType = Geolocation(
                    rawContent = "48.85852536332933, 2.294459839289565",
                )
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewWifi() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(
                qrType = Wifi(
                    rawContent = "WIFI:T:WPA2;S:wifi-5G;P:qwerty@123;;",
                    ssid = "wifi-5G",
                    password = "qwerty@123",
                    encryption = "WPA2"
                )
            ),
            onAction = {}
        )
    }
}