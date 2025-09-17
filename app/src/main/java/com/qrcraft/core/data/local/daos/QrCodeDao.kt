package com.qrcraft.core.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.qrcraft.core.data.local.entities.QrCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QrCodeDao {

    @Insert
    suspend fun insert(qrCode: QrCodeEntity): Long

    @Query("SELECT * FROM qr_codes WHERE id = :id")
    suspend fun getQrCode(id: Int): QrCodeEntity?

    @Upsert
    suspend fun upsert(qrCode: QrCodeEntity)

    @Delete
    suspend fun delete(qrCode: QrCodeEntity)

    @Query("SELECT * FROM qr_codes WHERE scannedOrGenerated = :typeValue ORDER BY isFavourite DESC, createdAt DESC")
    fun getQrCodes(typeValue: Int): Flow<List<QrCodeEntity>>

}