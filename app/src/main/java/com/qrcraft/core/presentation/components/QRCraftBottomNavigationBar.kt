package com.qrcraft.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.SurfaceHigher

@Composable
fun QRCraftBottomNavigationBar(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {

        Row(
            modifier = modifier
                .background(SurfaceHigher, RoundedCornerShape(100.dp))
                .padding(4.dp)

        ) {
            QRCraftBottomNavigationBarSecondaryButton(
                iconRes = R.drawable.ic_history,
                contentDescription = "History"
            )

            Spacer(modifier = Modifier.width(72.dp))

            QRCraftBottomNavigationBarSecondaryButton(
                iconRes = R.drawable.ic_create,
                contentDescription = "Create"
            )
        }
        IconButton(
            onClick = {},
            modifier = modifier
                .size(64.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(100.dp))
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_scan),
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "Scan",
                modifier = Modifier
                    .size(28.dp)
            )
        }
    }
}

@Composable
fun QRCraftBottomNavigationBarSecondaryButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = {},
        modifier = modifier
            .size(44.dp)
            //.background(Red)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(16.dp)
        )
    }
}

@Preview
@Composable
private fun QRCraftBottomNavigationBarPreview() {
    QRCraftTheme {
        QRCraftBottomNavigationBar()
    }
}