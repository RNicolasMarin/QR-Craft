package com.qrcraft.core.presentation.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val scanResult: DimensScanResult
)

data class DimensScanResult(
    val topBar: DimensScanResultTopBar,
    val scannedContent: DimensScanResultScannedContent
)

data class DimensScanResultTopBar(
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
    scanResult = DimensScanResult(
        topBar = DimensScanResultTopBar(
            paddingStart = 16.dp,
            paddingEnd = 16.dp,
            spaceEnd = 24.dp
        ),
        scannedContent = DimensScanResultScannedContent(
            qr = 160.dp,
            paddingStart = 16.dp,
            paddingEnd = 16.dp,
            paddingTop = 20.dp,
            paddingBottom = 16.dp
        )
    )
)

val dimensWider = Dimens(
    scanResult = DimensScanResult(
        topBar = DimensScanResultTopBar(
            paddingStart = 24.dp,
            paddingEnd = 24.dp,
            spaceEnd = 32.dp
        ),
        scannedContent = DimensScanResultScannedContent(
            qr = 200.dp,
            paddingStart = 24.dp,
            paddingEnd = 24.dp,
            paddingTop = 24.dp,
            paddingBottom = 24.dp
        )
    )
)