package com.qrcraft.create.presentation.data_entry

sealed interface DataEntryEvent {

    data class GoToPreview(
        val content: String
    ): DataEntryEvent

}