package com.qrcraft.scan.presentation.scan_result_preview

import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.BaseComponentAction.*
import com.qrcraft.core.presentation.components.QRCraftTopBarConfig
import com.qrcraft.core.presentation.components.QrCraftBaseComponent
import com.qrcraft.core.presentation.components.SnackBarType.*
import com.qrcraft.core.presentation.designsystem.DimensScanResultScannedContent
import com.qrcraft.core.presentation.designsystem.LinkBg
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.OnSurfaceDisabled
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.ScreenConfiguration
import com.qrcraft.core.presentation.designsystem.SurfaceHigher
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.core.presentation.designsystem.rememberKeyboardVisibility
import com.qrcraft.core.presentation.designsystem.screenConfiguration
import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.QrCodeType
import com.qrcraft.scan.domain.QrCodeType.*
import com.qrcraft.scan.domain.QrTypeTextStateDomain
import com.qrcraft.scan.presentation.scan_result_preview.QrTypeTextState.*
import com.qrcraft.scan.presentation.scan_result_preview.ScanResultPreviewAction.*
import com.qrcraft.scan.presentation.util.copyContent
import com.qrcraft.scan.presentation.util.generateQrCode
import com.qrcraft.scan.presentation.util.getFormattedContentResultPreview
import com.qrcraft.scan.presentation.util.getStringRes
import com.qrcraft.scan.presentation.util.opeLink
import com.qrcraft.scan.presentation.util.shareContent
import com.qrcraft.scan.presentation.util.tryToDownloadQrCodeAsImage
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScanResultPreviewScreenRoot(
    titleRes: Int,
    qrCodeId: Int,
    onBackPressed: () -> Unit,
    viewModel: ScanResultPreviewViewModel = koinViewModel()
) {

    LaunchedEffect(true) {
        viewModel.onAction(
            SetNonSavedQrContent(
                qrCodeId = qrCodeId
            )
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onAction(OnScreenRemoved)
        }
    }

    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    val scope = rememberCoroutineScope()
    var snackBarMessage by remember { mutableStateOf<String?>(null) }

    ScanResultPreviewScreen(
        snackBarMessage = snackBarMessage,
        titleRes = titleRes,
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                GoBack -> onBackPressed()
                is ShareContent -> {
                    context.shareContent(action.qrContent)
                }
                is CopyContent -> {
                    context.copyContent(clipboardManager, action.qrContent)
                }
                is OpenLink -> {
                    context.opeLink(action.link)
                }
                is SaveQrImage -> {
                    scope.launch {
                        val isSuccess = context.tryToDownloadQrCodeAsImage(action.bitmap)
                        snackBarMessage = if (isSuccess) DOWNLOAD_SUCCESS.text else DOWNLOAD_ERROR.text
                    }
                }
                ClearMessageSnackBar -> {
                    snackBarMessage = null
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultPreviewScreen(
    snackBarMessage: String?,
    titleRes: Int,
    state: ScanResultPreviewState,
    onAction: (ScanResultPreviewAction) -> Unit,
) {
    BackHandler {
        onAction(GoBack)
    }

    QrCraftBaseComponent(
        color = MaterialTheme.colorScheme.onSurface,
        topBarConfig = QRCraftTopBarConfig(
            titleRes = titleRes,
            color = OnOverlay,
            rightIconRes = if (state.qrCode?.isFavourite == true) R.drawable.ic_favourite_checked else R.drawable.ic_favourite_unchecked,
        ),
        snackBarMessage = snackBarMessage,
        onAction = {
            when (it) {
                TopBarOnBackClicked -> onAction(GoBack)
                TopBarOnRightClicked -> onAction(CheckUncheckFavourite)
                SnackBarClearMessage -> onAction(ClearMessageSnackBar)
                else -> Unit
            }
        }
    ) {
        ScanResultPreviewScreenContentAndImage(
            state = state,
            onAction = onAction
        )
    }
}

@Composable
fun ScanResultPreviewScreenContentAndImage(
    state: ScanResultPreviewState,
    onAction: (ScanResultPreviewAction) -> Unit,
    dimens: DimensScanResultScannedContent = MaterialTheme.dimen.scanResult.scannedContent,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    val scrollState = rememberScrollState()

    val rawContent = state.qrCode?.rawContent
    val qrBitmap = remember(rawContent) { rawContent?.let { generateQrCode(it) } }

    val contentModifier = Modifier
        .fillMaxSize()
        .padding(start = dimens.paddingStart, end = dimens.paddingEnd, bottom = 16.dp)
        .then(
            if (screenConfiguration != ScreenConfiguration.LANDSCAPE) Modifier.verticalScroll(scrollState) else Modifier
        )

    if (screenConfiguration == ScreenConfiguration.LANDSCAPE) {
        Row(
            modifier = contentModifier
        ) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.width(dimens.qr / 2))

                    QrContentContent(
                        state = state,
                        qrBitmap = qrBitmap,
                        onAction = onAction,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )

                    Spacer(modifier = Modifier.width(dimens.qr / 4))
                }
                QrCodeImage(
                    qrBitmap = qrBitmap
                )
            }
        }
    } else {
        Column(
            modifier = contentModifier
        ) {
            Spacer(modifier = Modifier.height(44.dp))
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                ) {
                    Spacer(modifier = Modifier.height(dimens.qr / 2))

                    QrContentContent(
                        state = state,
                        qrBitmap = qrBitmap,
                        onAction = onAction,
                        modifier = Modifier.width(480.dp)
                    )
                }
                QrCodeImage(
                    qrBitmap = qrBitmap
                )
            }
        }
    }
}

@Composable
fun QrCodeImage(
    qrBitmap: Bitmap?,
    dimens: DimensScanResultScannedContent = MaterialTheme.dimen.scanResult.scannedContent,
) {
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

@Composable
fun QrContentContent(
    state: ScanResultPreviewState,
    qrBitmap: Bitmap?,
    onAction: (ScanResultPreviewAction) -> Unit,
    modifier: Modifier = Modifier,
    dimens: DimensScanResultScannedContent = MaterialTheme.dimen.scanResult.scannedContent,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    val mod = modifier
        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
        .padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = 20.dp
        )
    if (screenConfiguration == ScreenConfiguration.LANDSCAPE) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = mod
        ) {
            Spacer(modifier = Modifier.width(dimens.qr / 2))

            QrContentInnerContent(
                state = state,
                qrBitmap = qrBitmap,
                onAction = onAction,
                modifier = Modifier.fillMaxHeight()
            )
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = mod
        ) {
            Spacer(modifier = Modifier.height(dimens.qr / 2))

            QrContentInnerContent(
                state = state,
                qrBitmap = qrBitmap,
                onAction = onAction,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun QrContentInnerContent(
    state: ScanResultPreviewState,
    qrBitmap: Bitmap?,
    onAction: (ScanResultPreviewAction) -> Unit,
    modifier: Modifier = Modifier,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        state.qrCode?.let {
            ScanResultPreviewTextField(
                qrCode = it,
                onTextChange = {
                    onAction(UpdateTitle(it))
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            val formattedContent = it.getFormattedContentResultPreview()

            val textAndButtonModifier = if (screenConfiguration == ScreenConfiguration.LANDSCAPE) {
                val scrollState = rememberScrollState()
                Modifier.weight(1f).verticalScroll(scrollState)
            } else {
                Modifier
            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = textAndButtonModifier
            ) {

                val infoModifier = when(it.type) {
                    is Text -> Modifier.fillMaxWidth()
                    is Link -> Modifier
                        .background(LinkBg)
                        .clickable(
                            onClick = { onAction(OpenLink(it.rawContent)) }
                        )
                    else -> Modifier
                }

                val uiTextState = state.textState.toUi()

                if (it.type !is Text && uiTextState == TEXT_SHORT) {
                    onAction(UpdateTextState(QrTypeTextStateDomain.NOT_TEXT))
                }

                Text(
                    text = formattedContent,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = if (it.type is Text) TextAlign.Start else TextAlign.Center,
                    maxLines = uiTextState.maxLines,
                    overflow = uiTextState.overflow,
                    onTextLayout = { textLayoutResult ->
                        if (uiTextState == NOT_TEXT) return@Text

                        if (uiTextState == TEXT_SHORT && textLayoutResult.hasVisualOverflow) {
                            onAction(UpdateTextState(QrTypeTextStateDomain.TEXT_TRUNCATED))
                        }
                    },
                    modifier = infoModifier
                )

                ShowMoreLessButton(
                    textState = uiTextState,
                    onClick = {
                        onAction(UpdateTextState(textState = when(uiTextState) {
                            NOT_TEXT -> NOT_TEXT
                            TEXT_SHORT -> TEXT_SHORT
                            TEXT_TRUNCATED -> TEXT_COMPLETED
                            TEXT_COMPLETED -> TEXT_TRUNCATED
                        }.toDomain()))
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            ShareCopyAndSaveButtons(
                formattedContent = formattedContent,
                qrBitmap = qrBitmap,
                onAction = onAction
            )
        }
    }
}

@Composable
fun ScanResultPreviewTextField(
    qrCode: QrCode,
    onTextChange: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val isKeyboardVisible by rememberKeyboardVisibility()

    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible) {
            focusManager.clearFocus()
        }
    }

    BasicTextField(
        value = when {
            //if focused and empty show hint, if not empty show the content
            isFocused -> qrCode.title
            //if not focused show res if empty or title
            else -> qrCode.title.ifBlank { stringResource(qrCode.type.getStringRes()) }
        },
        onValueChange = { text -> onTextChange(text) },
        textStyle = MaterialTheme.typography.titleMedium.copy(
            textAlign = TextAlign.Center
        ),
        modifier = modifier
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (isFocused && qrCode.title.isBlank()) {
                    Text(
                        text = stringResource(qrCode.type.getStringRes()),
                        style = MaterialTheme.typography.titleMedium.copy(
                            textAlign = TextAlign.Center
                        ),
                        color = Color(0xFFC5CBCF),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun ShowMoreLessButton(
    textState: QrTypeTextState,
    onClick: () -> Unit
) {
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
                    .clickable(onClick = onClick)
            )
        }
    }
}

@Composable
fun ShareCopyAndSaveButtons(
    formattedContent: String,
    qrBitmap: Bitmap?,
    onAction: (ScanResultPreviewAction) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        ScanResultScannedContentActionButton(
            modifier = Modifier.size(44.dp),
            textRes = null,
            iconRes = R.drawable.ic_share,
            onClick = { onAction(ShareContent(formattedContent)) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        ScanResultScannedContentActionButton(
            modifier = Modifier.size(44.dp),
            textRes = null,
            iconRes = R.drawable.ic_copy,
            onClick = { onAction(CopyContent(formattedContent)) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        ScanResultScannedContentActionButton(
            modifier = Modifier.weight(1f).size(44.dp),
            textRes = R.string.scan_result_save,
            iconRes = R.drawable.ic_save,
            onClick = {
                qrBitmap?.let { bitmap ->
                    onAction(SaveQrImage(bitmap))
                }
            }
        )
    }
}

@Composable
fun ScanResultScannedContentActionButton(
    modifier: Modifier = Modifier,
    @StringRes textRes: Int?,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(100.dp),
        color = SurfaceHigher,
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = textRes?.let { stringResource(it) } ?: run { "Icon" },
                modifier = Modifier.size(16.dp)
            )

            textRes?.let {
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = stringResource(textRes),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewText() {
    QRCraftTheme {
        ScanResultPreviewScreen(
            snackBarMessage = null,
            titleRes = R.string.preview,
            state = ScanResultPreviewState(
                qrCode = QrCode(
                    rawContent = "Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum justo metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium. Adipiscing ipsum lacinia tincidunt sed. In risus dui accumsan accumsan quam morbi nulla. Dictum metus auctor nunc quam id sed. Urna nisi gravida sed lobortis diam pretium.",
                    type = QrCodeType.Text
                )
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewLink() {
    QRCraftTheme {
        ScanResultPreviewScreen(
            snackBarMessage = null,
            titleRes = R.string.preview,
            state = ScanResultPreviewState(
                qrCode = QrCode(
                    rawContent = "http://https://pl-coding.mymemberspot.io",
                    type = Link
                )
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun LoginScreenPreviewContact() {
    QRCraftTheme {
        ScanResultPreviewScreen(
            snackBarMessage = null,
            titleRes = R.string.preview,
            state = ScanResultPreviewState(
                qrCode = QrCode(
                    rawContent = "BEGIN:VCARD\nVERSION:3.0\nN:Olivia Schmidt\nTEL:+1 (555) 284-7390\nEMAIL:olivia.schmidt@example.com\nEND:VCARD",
                    type = Contact(
                        name = "Olivia Schmidt",
                        email = "olivia.schmidt@example.com",
                        phone = "+1 (555) 284-7390"
                    )
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
        ScanResultPreviewScreen(
            snackBarMessage = null,
            titleRes = R.string.preview,
            state = ScanResultPreviewState(
                qrCode = QrCode(
                    rawContent = "",
                    type = PhoneNumber("+49 170 1234567")
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
        ScanResultPreviewScreen(
            snackBarMessage = null,
            titleRes = R.string.preview,
            state = ScanResultPreviewState(
                qrCode = QrCode(
                    rawContent = "50.4501,30.5234",
                    type = Geolocation(
                        latitude = "50.4501",
                        longitude = "30.5234",
                    )
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
        ScanResultPreviewScreen(
            snackBarMessage = null,
            titleRes = R.string.preview,
            state = ScanResultPreviewState(
                qrCode = QrCode(
                    rawContent = "WIFI:S:DevHub_WiFi;T:WPA;P:QrCraft2025;H:false;;",
                    type = Wifi(
                        ssid = "DevHub_WiFi",
                        password = "QrCraft2025",
                        encryption = "WPA"
                    )
                )
            ),
            onAction = {}
        )
    }
}