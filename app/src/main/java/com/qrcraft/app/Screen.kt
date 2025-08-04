package com.qrcraft.app

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object Scan: Screen()

    @Serializable
    data object ScanResult: Screen()

}