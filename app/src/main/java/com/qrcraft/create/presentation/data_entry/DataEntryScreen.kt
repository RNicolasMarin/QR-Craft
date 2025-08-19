package com.qrcraft.create.presentation.data_entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.qrcraft.create.presentation.create_qr.QrTypeUI
import com.qrcraft.create.presentation.create_qr.QrTypeUI.*

@Composable
fun DataEntryScreenRoot(
    qrTypeOrdinal: Int
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = (QrTypeUI.entries.getOrNull(qrTypeOrdinal) ?: TEXT).name)
    }
}