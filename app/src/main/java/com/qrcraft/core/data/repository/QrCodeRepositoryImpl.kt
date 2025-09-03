package com.qrcraft.core.data.repository

import com.qrcraft.core.data.local.daos.QrCodeDao
import com.qrcraft.core.data.local.entities.toEntity
import com.qrcraft.core.domain.QrCodeRepository
import com.qrcraft.scan.domain.QrCode

class QrCodeRepositoryImpl(
    private val qrCodeDao: QrCodeDao
): QrCodeRepository {

    override suspend fun upsert(qrCode: QrCode) {
        qrCodeDao.upsert(qrCode.toEntity())
    }
}