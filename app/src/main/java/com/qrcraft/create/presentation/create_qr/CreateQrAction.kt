package com.qrcraft.create.presentation.create_qr

sealed interface CreateQrAction {

    data class OnDataEntry(
        val qrTypeOrdinal: Int
    ): CreateQrAction
}