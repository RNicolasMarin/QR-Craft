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
import com.qrcraft.core.presentation.designsystem.DimensBottomBar
import com.qrcraft.core.presentation.designsystem.LinkBg
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.SurfaceHigher
import com.qrcraft.core.presentation.designsystem.dimen

@Composable
fun QRCraftBottomNavigationBar(
    modifier: Modifier = Modifier,
    isOnHistory: Boolean = false,
    isOnCreating: Boolean = false,
    onCreate: () -> Unit,
    onHistory: () -> Unit,
    dimens: DimensBottomBar = MaterialTheme.dimen.bottomBar
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {

        Row(
            modifier = modifier
                .background(SurfaceHigher, RoundedCornerShape(100.dp))
                .padding(dimens.padding)

        ) {
            QRCraftBottomNavigationBarSecondaryButton(
                iconRes = R.drawable.ic_history,
                contentDescription = "History",
                isHighlighting = isOnHistory,
                onClick = onHistory
            )

            Spacer(modifier = Modifier.width(dimens.spaceBetween))

            QRCraftBottomNavigationBarSecondaryButton(
                iconRes = R.drawable.ic_create,
                contentDescription = "Create",
                isHighlighting = isOnCreating,
                onClick = onCreate
            )
        }
        IconButton(
            onClick = {},
            modifier = modifier
                .size(dimens.scanOuter)
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
    onClick: () -> Unit,
    isHighlighting: Boolean,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(44.dp)
            .background(if (isHighlighting) LinkBg else SurfaceHigher, RoundedCornerShape(44.dp))
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
        QRCraftBottomNavigationBar(
            isOnHistory = true,
            isOnCreating = true,
            onCreate = {},
            onHistory = {}
        )
    }
}