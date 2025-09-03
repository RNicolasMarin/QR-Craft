package com.qrcraft.create.presentation.data_entry

import com.qrcraft.scan.domain.QrCode

data class DataEntryState(
    val qrCode: QrCode? = null,
    val canGenerate: Boolean = false
)
