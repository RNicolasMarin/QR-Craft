package com.qrcraft.create.presentation.data_entry

import com.qrcraft.scan.domain.QrType

data class DataEntryState(
    val qrType: QrType? = null,
    val canGenerate: Boolean = false
)
