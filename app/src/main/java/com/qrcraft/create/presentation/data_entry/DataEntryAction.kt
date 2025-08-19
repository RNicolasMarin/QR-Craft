package com.qrcraft.create.presentation.data_entry

sealed interface DataEntryAction {

    data object GoBackToCreateQr: DataEntryAction
}