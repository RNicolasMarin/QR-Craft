package com.qrcraft.core.presentation.components

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
import com.qrcraft.core.presentation.components.BaseComponentAction.*
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.core.presentation.designsystem.statusBarHeight

@Composable
fun QRCraftTopBar(
    modifier: Modifier = Modifier,
    dimens: DimensTopBar = MaterialTheme.dimen.topBar,
    config: QRCraftTopBarConfig,
    onAction: (BaseComponentAction) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(start = dimens.paddingStart, end = dimens.paddingEnd)
    ) {
        Spacer(
            modifier = Modifier.height(statusBarHeight())
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 8.dp)
        ) {
            if (config.backIconRes != null) {
                Icon(
                    painter = painterResource(id = config.backIconRes),
                    tint = config.color,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(onClick = {
                            onAction(TopBarOnBackClicked)
                        })
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
                text = stringResource(config.titleRes),
                style = MaterialTheme.typography.titleMedium,
                color = config.color,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            if (config.rightIconRes != null) {
                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Icon(
                    painter = painterResource(id = config.rightIconRes),
                    tint = config.color,
                    contentDescription = "Right Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            onClick = {
                                onAction(TopBarOnRightClicked)
                            }
                        )
                )
            } else {
                Spacer(
                    modifier = Modifier.width(dimens.spaceEnd)
                )
            }
        }
    }
}

data class QRCraftTopBarConfig(
    val titleRes: Int,
    val color: Color,
    val backIconRes: Int? = R.drawable.arrow_left,
    val rightIconRes: Int? = null,
)

@Preview
@Composable
private fun QRCraftTopBarPreviewScanResultScreenFavourite() {
    QRCraftTheme {
        QRCraftTopBar(
            config = QRCraftTopBarConfig(
                titleRes = R.string.scan_result,
                color = OnOverlay,
                rightIconRes = R.drawable.ic_favourite_checked
            )
        )
    }
}

@Preview
@Composable
private fun QRCraftTopBarPreviewScanResultScreenNotFavourite() {
    QRCraftTheme {
        QRCraftTopBar(
            config = QRCraftTopBarConfig(
                titleRes = R.string.scan_result,
                color = OnOverlay,
                rightIconRes = R.drawable.ic_favourite_unchecked
            )
        )
    }
}

@Preview
@Composable
private fun QRCraftTopBarPreviewCreateQrScreen() {
    QRCraftTheme {
        QRCraftTopBar(
            config = QRCraftTopBarConfig(
                titleRes = R.string.create_qr_title,
                color = MaterialTheme.colorScheme.onSurface,
                backIconRes = null
            )
        )
    }
}

@Preview
@Composable
private fun QRCraftTopBarPreviewDataEntryScreen() {
    QRCraftTheme {
        QRCraftTopBar(
            config = QRCraftTopBarConfig(
                titleRes = R.string.qr_type_text,
                color = MaterialTheme.colorScheme.onSurface,
            )
        )
    }
}

@Preview
@Composable
private fun QRCraftTopBarScanHistoryScreen() {
    QRCraftTheme {
        QRCraftTopBar(
            config = QRCraftTopBarConfig(
                titleRes = R.string.scan_history_title,
                color = MaterialTheme.colorScheme.onSurface,
                backIconRes = null
            )
        )
    }
}