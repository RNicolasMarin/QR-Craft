package com.qrcraft.core.presentation.designsystem

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun QRCraftDialog(
    @StringRes title: Int,
    @StringRes text: Int,
    @StringRes confirmButton: Int,
    @StringRes dismissButton: Int,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    AlertDialog(
        shape = RoundedCornerShape(12.dp),
        containerColor = MaterialTheme.colorScheme.surface,
        titleContentColor = MaterialTheme.colorScheme.onSurface,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                QRCraftDialogButton(
                    text = dismissButton,
                    color = MaterialTheme.colorScheme.error,
                    onClick = onDismissButtonClick,
                    modifier = Modifier.weight(1f)
                )
                QRCraftDialogButton(
                    text = confirmButton,
                    color = MaterialTheme.colorScheme.onSurface,
                    onClick = onConfirmButtonClick,
                    modifier = Modifier.weight(1f)
                )
            }
        },
        dismissButton = { }
    )
}

@Composable
fun QRCraftDialogButton(
    modifier: Modifier = Modifier,
    text: Int,
    color: Color,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(100.dp),
        colors = ButtonDefaults.textButtonColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = color
        ),
        modifier = modifier
    ) {
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.labelLarge,
            color = color,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
    }
}