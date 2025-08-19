package com.qrcraft.core.presentation.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val scanResult: DimensScanResult,
    val createQr: DimensCreateQr,
    val topBar: DimensTopBar,
)

data class DimensScanResult(
    val scannedContent: DimensScanResultScannedContent
)

data class DimensCreateQr(
    val columnsAmount: Int
)

data class DimensTopBar(
    val paddingStart: Dp,
    val paddingEnd: Dp,
    val spaceEnd: Dp
)

data class DimensScanResultScannedContent(
    val qr: Dp,
    val paddingStart: Dp,
    val paddingEnd: Dp,
    val paddingTop: Dp,
    val paddingBottom: Dp,
)

val dimensMobile = Dimens(
    topBar = DimensTopBar(
        paddingStart = 16.dp,
        paddingEnd = 16.dp,
        spaceEnd = 24.dp
    ),
    scanResult = DimensScanResult(
        scannedContent = DimensScanResultScannedContent(
            qr = 160.dp,
            paddingStart = 16.dp,
            paddingEnd = 16.dp,
            paddingTop = 20.dp,
            paddingBottom = 16.dp
        )
    ),
    createQr = DimensCreateQr(
        columnsAmount = 2
    )
)

val dimensWider = Dimens(
    topBar = DimensTopBar(
        paddingStart = 24.dp,
        paddingEnd = 24.dp,
        spaceEnd = 32.dp
    ),
    scanResult = DimensScanResult(
        scannedContent = DimensScanResultScannedContent(
            qr = 200.dp,
            paddingStart = 24.dp,
            paddingEnd = 24.dp,
            paddingTop = 24.dp,
            paddingBottom = 24.dp
        )
    ),
    createQr = DimensCreateQr(
        columnsAmount = 3
    )
)