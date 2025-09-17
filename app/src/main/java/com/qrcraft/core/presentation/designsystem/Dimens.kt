package com.qrcraft.core.presentation.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val scan: DimensScan,
    val scanResult: DimensScanResult,
    val createQr: DimensCreateQr,
    val dataEntry: DimensDataEntry,
    val topBar: DimensTopBar,
    val bottomBar: DimensBottomBar
)

data class DimensScan(
    val iconButtonSize: Dp
)

data class DimensScanResult(
    val scannedContent: DimensScanResultScannedContent
)

data class DimensCreateQr(
    val columnsAmount: Int
)

data class DimensDataEntry(
    val spaceTopBarAndContent: Dp,
    val padding: Dp
)

data class DimensTopBar(
    val paddingStart: Dp,
    val paddingEnd: Dp,
    val spaceEnd: Dp
)

data class DimensBottomBar(
    val padding: Dp,
    val spaceBetween: Dp,
    val scanOuter: Dp,
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
    bottomBar = DimensBottomBar(
        padding = 4.dp,
        spaceBetween = 80.dp,
        scanOuter = 64.dp,
    ),
    scan = DimensScan(
        iconButtonSize = 44.dp
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
    ),
    dataEntry = DimensDataEntry(
        spaceTopBarAndContent = 0.dp,
        padding = 16.dp
    )
)

val dimensWider = Dimens(
    topBar = DimensTopBar(
        paddingStart = 24.dp,
        paddingEnd = 24.dp,
        spaceEnd = 32.dp
    ),
    bottomBar = DimensBottomBar(
        padding = 8.dp,
        spaceBetween = 72.dp,
        scanOuter = 72.dp,
    ),
    scan = DimensScan(
        iconButtonSize = 48.dp
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
    ),
    dataEntry = DimensDataEntry(
        spaceTopBarAndContent = 12.dp,
        padding = 24.dp
    )
)