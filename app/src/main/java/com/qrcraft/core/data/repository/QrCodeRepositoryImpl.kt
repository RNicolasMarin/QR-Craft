package com.qrcraft.core.data.repository

import com.qrcraft.core.data.local.daos.QrCodeDao
import com.qrcraft.core.data.local.entities.toDomain
import com.qrcraft.core.data.local.entities.toEntity
import com.qrcraft.core.domain.QrCodeRepository
import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.ScannedOrGenerated
import com.qrcraft.scan.domain.ScannedOrGenerated.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class QrCodeRepositoryImpl(
    private val qrCodeDao: QrCodeDao
): QrCodeRepository {

    override suspend fun insert(qrCode: QrCode): Int {
        return qrCodeDao.insert(qrCode.toEntity()).toInt()
    }

    override suspend fun getQrCode(qrCodeId: Int): QrCode? {
        return qrCodeDao.getQrCode(qrCodeId)?.toDomain()
    }

    override suspend fun upsert(qrCode: QrCode) {
        qrCodeDao.upsert(qrCode.toEntity())
    }

    override suspend fun delete(qrCode: QrCode) {
        qrCodeDao.delete(qrCode.toEntity())
    }

    override fun getQrCodesScanned(scannedOrGenerated: ScannedOrGenerated): Flow<List<QrCode>> {
        return if (scannedOrGenerated == SCANNED) {
            qrCodeDao.getQrCodes(SCANNED.typeValue).map { list ->
                list.map { qrCodeEntity ->
                    qrCodeEntity.toDomain()
                }
            }
        } else {
            emptyFlow()
        }
    }

    override fun getQrCodesGenerated(scannedOrGenerated: ScannedOrGenerated): Flow<List<QrCode>> {
        return if (scannedOrGenerated == GENERATED) {
            qrCodeDao.getQrCodes(GENERATED.typeValue).map { list ->
                list.map { qrCodeEntity ->
                    qrCodeEntity.toDomain()
                }
            }
        } else {
            emptyFlow()
        }
    }
}