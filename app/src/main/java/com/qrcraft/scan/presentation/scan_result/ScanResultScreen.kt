package com.qrcraft.scan.presentation.scan_result

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.statusBarHeight

@Composable
fun ScanResultScreenRoot(
    onBackToScan: () -> Unit
) {
    ScanResultScreen(onBackToScan)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanResultScreen(onBackToScan: () -> Unit) {
    BackHandler {
        onBackToScan()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface),
    ) {
        Spacer(
            modifier = Modifier.height(statusBarHeight())
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            IconButton(
                onClick = onBackToScan
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    tint = OnOverlay,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            Text(
                text = stringResource(R.string.scan_result),
                style = MaterialTheme.typography.titleMedium,
                color = OnOverlay,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(
                modifier = Modifier.width(24.dp)
            )
        }
    }
}