package com.qrcraft.core.data.local.daos

import androidx.room.Dao
import androidx.room.Upsert
import com.qrcraft.core.data.local.entities.QrCodeEntity

@Dao
interface QrCodeDao {

    @Upsert
    suspend fun upsert(qrCode: QrCodeEntity)

}