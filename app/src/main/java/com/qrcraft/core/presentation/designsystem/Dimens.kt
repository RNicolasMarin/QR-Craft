package com.qrcraft.core.presentation.designsystem

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val scan: DimensScan,
    val scanResult: DimensScanResult,
    val createQr: DimensCreateQr,
    val dataEntry: DimensDataEntry,
    val history: DimensHistory,
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
    val columnsAmount: Int,
    val startPadding: Dp,
    val endPadding: Dp,
    val bottomSpace: Dp,
)

data class DimensHistory(
    val columnsAmount: Int,
    val startPadding: Dp,
    val endPadding: Dp,
    val bottomSpace: Dp,
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

val dimensPhonePortrait = Dimens(
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
        columnsAmount = 2,
        startPadding = 0.dp,
        endPadding = 0.dp,
        bottomSpace = 64.dp
    ),
    dataEntry = DimensDataEntry(
        spaceTopBarAndContent = 0.dp,
        padding = 16.dp
    ),
    history = DimensHistory(
        columnsAmount = 1,
        startPadding = 16.dp,
        endPadding = 16.dp,
        bottomSpace = 64.dp
    )
)

val dimensTabletPortrait = Dimens(
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
        columnsAmount = 3,
        startPadding = 0.dp,
        endPadding = 0.dp,
        bottomSpace = 72.dp
    ),
    dataEntry = DimensDataEntry(
        spaceTopBarAndContent = 12.dp,
        padding = 24.dp
    ),
    history = DimensHistory(
        columnsAmount = 2,
        startPadding = 24.dp,
        endPadding = 24.dp,
        bottomSpace = 72.dp
    )
)

val dimensLandscape = Dimens(
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
            paddingStart = 8.dp,
            paddingEnd = 8.dp,
            paddingTop = 20.dp,
            paddingBottom = 16.dp
        )
    ),
    createQr = DimensCreateQr(
        columnsAmount = 3,
        startPadding = 8.dp,
        endPadding = 64.dp,
        bottomSpace = 0.dp
    ),
    dataEntry = DimensDataEntry(
        spaceTopBarAndContent = 0.dp,
        padding = 16.dp
    ),
    history = DimensHistory(
        columnsAmount = 2,
        startPadding = 8.dp,
        endPadding = 64.dp,
        bottomSpace = 0.dp
    )
)