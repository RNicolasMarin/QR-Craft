package com.qrcraft.core.domain

import com.qrcraft.scan.domain.QrCode

interface QrCodeRepository {

    suspend fun upsert(qrCode: QrCode)
}