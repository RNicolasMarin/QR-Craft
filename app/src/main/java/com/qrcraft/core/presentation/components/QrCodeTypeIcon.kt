package com.qrcraft.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.qrcraft.create.presentation.create_qr.QrTypeUI

@Composable
fun QrCodeTypeIcon(
    item: QrTypeUI,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .background(item.iconBackColor, RoundedCornerShape(100.dp))
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(id = item.iconRes),
            tint = item.iconColor,
            contentDescription = stringResource(item.textRes),
            modifier = Modifier
                .size(16.dp)
        )
    }
}