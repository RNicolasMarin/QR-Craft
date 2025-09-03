package com.qrcraft.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qrcraft.scan.domain.QrCode
import kotlin.String

@Entity(tableName = "qr_codes")
data class QrCodeEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var rawContent: String,
    var title: String,
    var type: Int,
    var createdAt: Long
)

fun QrCode.toEntity() = QrCodeEntity(
    id = id,
    rawContent = rawContent,
    title = title,
    type = type.typeCode,
    createdAt = createdAt
)
