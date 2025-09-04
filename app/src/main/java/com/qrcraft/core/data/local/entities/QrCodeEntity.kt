package com.qrcraft.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qrcraft.core.domain.QrCodeTypeConverter
import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.ScannedOrGenerated
import kotlin.String

@Entity(tableName = "qr_codes")
data class QrCodeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var rawContent: String,
    var title: String,
    var type: Int,
    var createdAt: Long,
    var scannedOrGenerated: Int
)

fun QrCode.toEntity() = QrCodeEntity(
    id = if (id == -1) null else id,
    rawContent = rawContent,
    title = title,
    type = type.typeId,
    createdAt = createdAt,
    scannedOrGenerated = scannedOrGenerated.typeValue
)

fun QrCodeEntity.toDomain() = QrCode(
    id = id ?: -1,
    rawContent = rawContent,
    title = title,
    type = QrCodeTypeConverter().convertToType(type, rawContent),
    createdAt = createdAt,
    scannedOrGenerated = ScannedOrGenerated.entries.first { it.typeValue == scannedOrGenerated }
)
