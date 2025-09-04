package com.qrcraft.core.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.qrcraft.core.data.local.entities.QrCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QrCodeDao {

    @Upsert
    suspend fun upsert(qrCode: QrCodeEntity)

    @Query("SELECT * FROM qr_codes WHERE scannedOrGenerated = :typeValue")
    fun getQrCodes(typeValue: Int): Flow<List<QrCodeEntity>>

}