package com.qrcraft.core.domain

import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.ScannedOrGenerated
import kotlinx.coroutines.flow.Flow

interface QrCodeRepository {

    suspend fun upsert(qrCode: QrCode)

    fun getQrCodesScanned(scannedOrGenerated: ScannedOrGenerated): Flow<List<QrCode>>

    fun getQrCodesGenerated(scannedOrGenerated: ScannedOrGenerated): Flow<List<QrCode>>

}