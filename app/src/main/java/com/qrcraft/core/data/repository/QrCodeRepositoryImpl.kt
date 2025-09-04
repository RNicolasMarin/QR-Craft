package com.qrcraft.core.data.repository

import com.qrcraft.core.data.local.daos.QrCodeDao
import com.qrcraft.core.data.local.entities.toDomain
import com.qrcraft.core.data.local.entities.toEntity
import com.qrcraft.core.domain.QrCodeRepository
import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.ScannedOrGenerated
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QrCodeRepositoryImpl(
    private val qrCodeDao: QrCodeDao
): QrCodeRepository {

    override suspend fun upsert(qrCode: QrCode) {
        qrCodeDao.upsert(qrCode.toEntity())
    }

    override fun getQrCodes(scannedOrGenerated: ScannedOrGenerated): Flow<List<QrCode>> {
        return qrCodeDao.getQrCodes(scannedOrGenerated.typeValue).map { list ->
            list.map { qrCodeEntity ->
                qrCodeEntity.toDomain()
            }
        }
    }
}