package com.qrcraft.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.core.presentation.designsystem.statusBarHeight

@Composable
fun QRCraftTopBar(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    color: Color,
    onBackClicked: (() -> Unit)? = null,
    dimens: DimensTopBar = MaterialTheme.dimen.topBar
) {
    Column(
        modifier = modifier
    ) {
        Spacer(
            modifier = Modifier.height(statusBarHeight())
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            if (onBackClicked != null) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    tint = color,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = onBackClicked)
                )
                Spacer(
                    modifier = Modifier.width(8.dp)
                )
            } else {
                Spacer(
                    modifier = Modifier.width(32.dp)
                )
            }

            Text(
                text = stringResource(titleRes),
                style = MaterialTheme.typography.titleMedium,
                color = color,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(
                modifier = Modifier.width(dimens.spaceEnd)
            )
        }
    }
}

@Preview
@Composable
private fun QRCraftTopBarPreviewScanResultScreen() {
    QRCraftTheme {
        QRCraftTopBar(
            color = OnOverlay,
            titleRes = R.string.scan_result,
            onBackClicked = {}
        )
    }
}

@Preview
@Composable
private fun QRCraftTopBarPreviewCreateQrScreen() {
    QRCraftTheme {
        QRCraftTopBar(
            color = MaterialTheme.colorScheme.onSurface,
            titleRes = R.string.scan_result,
        )
    }
}

@Preview
@Composable
private fun QRCraftTopBarPreviewDataEntryScreen() {
    QRCraftTheme {
        QRCraftTopBar(
            color = MaterialTheme.colorScheme.onSurface,
            titleRes = R.string.scan_result,
            onBackClicked = {}
        )
    }
}