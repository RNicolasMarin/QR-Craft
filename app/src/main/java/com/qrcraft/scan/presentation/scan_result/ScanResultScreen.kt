package com.qrcraft.scan.presentation.scan_result

import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.QRCraftTopBar
import com.qrcraft.core.presentation.designsystem.DimensScanResultScannedContent
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.Grey2
import com.qrcraft.core.presentation.designsystem.LinkBg
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.OnSurfaceDisabled
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.SurfaceHigher
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.scan.domain.QrType
import com.qrcraft.scan.domain.QrType.*
import com.qrcraft.scan.presentation.scan_result.QrTypeTextState.*
import com.qrcraft.scan.presentation.scan_result.ScanResultAction.*
import com.qrcraft.scan.presentation.util.copyContent
import com.qrcraft.scan.presentation.util.generateQrCode
import com.qrcraft.scan.presentation.util.getFormattedContent
import com.qrcraft.scan.presentation.util.getStringRes
import com.qrcraft.scan.presentation.util.opeLink
import com.qrcraft.scan.presentation.util.shareContent
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

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    ScanResultScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                GoBackToScan -> onBackToScan()
                is ShareContent -> {
                    context.shareContent(action.qrContent)
                }
                is CopyContent -> {
                    context.copyContent(clipboardManager, action.qrContent)
                }
                is OpenLink -> {
                    context.opeLink(action.link)
                }
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
    onAction: (ScanResultAction) -> Unit,
    dimens: DimensTopBar = MaterialTheme.dimen.topBar
) {
    BackHandler {
        onAction(GoBackToScan)
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface)
            .padding(start = dimens.paddingStart, end = dimens.paddingEnd, bottom = 16.dp)
            .verticalScroll(scrollState),
    ) {
        QRCraftTopBar(
            color = OnOverlay,
            titleRes = R.string.scan_result,
            onBackClicked = { onAction(GoBackToScan) }
        )

        Spacer(modifier = Modifier.height(44.dp))

        ScanResultScannedContent(
            state = state,
            onAction = onAction,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ScanResultScannedContent(
    state: ScanResultState,
    onAction: (ScanResultAction) -> Unit,
    modifier: Modifier = Modifier,
    dimens: DimensScanResultScannedContent = MaterialTheme.dimen.scanResult.scannedContent
) {
    var textState by remember { mutableStateOf(TEXT_SHORT) }

    val rawContent = state.qrType?.rawContent
    val qrBitmap = remember(rawContent) { rawContent?.let { generateQrCode(it) } }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .width(480.dp)
        ) {
            Spacer(modifier = Modifier.height(dimens.qr / 2))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                        top = 20.dp
                    )
            ) {
                Spacer(modifier = Modifier.height(dimens.qr / 2))

                state.qrType?.let {
                    Text(
                        text = stringResource(it.getStringRes()),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val contentModifier = when(it) {
                        is QrType.Text -> Modifier.fillMaxWidth()
                        is Link -> Modifier
                            .background(LinkBg)
                            .clickable(
                                onClick = { onAction(OpenLink(it.rawContent)) }
                            )
                        else -> Modifier
                    }

                    if (it !is Text && textState == TEXT_SHORT) {
                        textState = NOT_TEXT
                    }

                    val formattedContent = it.getFormattedContent()

                    Text(
                        text = formattedContent,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = if (it is Text) TextAlign.Start else TextAlign.Center,
                        maxLines = textState.maxLines,
                        overflow = textState.overflow,
                        onTextLayout = { textLayoutResult ->
                            if (textState == NOT_TEXT) return@Text

                            if (textState == TEXT_SHORT && textLayoutResult.hasVisualOverflow) {
                                textState = TEXT_TRUNCATED
                            }
                        },
                        modifier = contentModifier
                    )

                    textState.buttonRes?.let { res ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = stringResource(res),
                                style = MaterialTheme.typography.labelLarge,
                                color = textState.color ?: OnSurfaceDisabled,
                                modifier = Modifier
                                    .clickable(
                                        onClick = {
                                            textState = when(textState) {
                                                NOT_TEXT -> NOT_TEXT
                                                TEXT_SHORT -> TEXT_SHORT
                                                TEXT_TRUNCATED -> TEXT_COMPLETED
                                                TEXT_COMPLETED -> TEXT_TRUNCATED
                                            }
                                        }
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ScanResultScannedContentActionButton(
                            modifier = Modifier.weight(1f),
                            textRes = R.string.scan_result_share,
                            iconRes = R.drawable.ic_share,
                            onClick = { onAction(ShareContent(formattedContent)) }
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        ScanResultScannedContentActionButton(
                            modifier = Modifier.weight(1f),
                            textRes = R.string.scan_result_copy,
                            iconRes = R.drawable.ic_copy,
                            onClick = { onAction(CopyContent(formattedContent)) }
                        )
                    }
                }
            }
        }


        qrBitmap?.let {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(dimens.qr)
                    .shadow(
                        elevation = 20.dp, // blur
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color(0x1A273037), // 10% opacity (#273037)
                        spotColor = Color(0x1A273037)     // same for spot
                    )
                    .background(SurfaceHigher, RoundedCornerShape(16.dp))
            ) {
                Image(
                    bitmap = qrBitmap.asImageBitmap(),
                    contentDescription = "QR Code",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(dimens.qr)
                )
            }
        }
    }
}

@Composable
fun ScanResultScannedContentActionButton(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Button(
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = SurfaceHigher
        ),
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(6.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = stringResource(textRes),
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = stringResource(textRes),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

enum class QrTypeTextState(val maxLines: Int, val overflow: TextOverflow, val buttonRes: Int?, val color: Color?){
    NOT_TEXT(Int.MAX_VALUE, TextOverflow.Clip, null, null),
    TEXT_SHORT(6, TextOverflow.Ellipsis, null, null),
    TEXT_TRUNCATED(6, TextOverflow.Ellipsis, R.string.qr_type_text_show_more, Grey2),
    TEXT_COMPLETED(Int.MAX_VALUE, TextOverflow.Clip, R.string.qr_type_text_show_less, OnSurfaceDisabled)
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
                qrType = Link("http://https://pl-coding.mymemberspot.io")
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
                    rawContent = "BEGIN:VCARD\nVERSION:3.0\nN:Olivia Schmidt\nTEL:+1 (555) 284-7390\nEMAIL:olivia.schmidt@example.com\nEND:VCARD",
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
private fun LoginScreenPreviewPhoneNumber() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(
                qrType = PhoneNumber(
                    rawContent = "+49 170 1234567",
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
                    rawContent = "50.4501,30.5234",
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
private fun LoginScreenPreviewWifi() {
    QRCraftTheme {
        ScanResultScreen(
            state = ScanResultState(
                qrType = Wifi(
                    rawContent = "WIFI:S:DevHub_WiFi;T:WPA;P:QrCraft2025;H:false;;",
                    ssid = "DevHub_WiFi",
                    password = "QrCraft2025",
                    encryption = "WPA"
                )
            ),
            onAction = {}
        )
    }
}