package com.qrcraft.core.presentation.components

sealed class InfoToShow {

    data object None: InfoToShow()

    data class RequestPermission(
        val title: Int,
        val text: Int,
        val confirmButton: Int,
        val dismissButton: Int,
    ): InfoToShow()

    data object Loading: InfoToShow()

    data object Error: InfoToShow()

}