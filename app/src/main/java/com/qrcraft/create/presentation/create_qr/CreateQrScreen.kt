package com.qrcraft.create.presentation.create_qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.QRCraftBottomNavigationBar
import com.qrcraft.core.presentation.components.QRCraftTopBar
import com.qrcraft.core.presentation.designsystem.DimensCreateQr
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.SurfaceHigher
import com.qrcraft.core.presentation.designsystem.dimen

@Composable
fun CreateQrScreenRoot() {
    CreateQrScreen()
}

@Composable
fun CreateQrScreen(
    dimens: DimensTopBar = MaterialTheme.dimen.topBar
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(8f)
                .padding(start = dimens.paddingStart, end = dimens.paddingEnd, bottom = 16.dp)
                .verticalScroll(scrollState),
        ) {
            QRCraftTopBar(
                color = MaterialTheme.colorScheme.onSurface,
                titleRes = R.string.create_qr_title,
            )
            CreateQrScreenGrid()
        }

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            QRCraftBottomNavigationBar(
                isOnCreating = true,
                onCreate = { }
            )
        }
    }
}

@Composable
fun CreateQrScreenGrid(
    modifier: Modifier = Modifier,
    dimens: DimensCreateQr = MaterialTheme.dimen.createQr
) {
    val items = QrTypeUI.entries.toList()

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        items.chunked(dimens.columnsAmount).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowItems.forEach { item ->
                    CreateQrScreenGridCell(item = item)
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
fun RowScope.CreateQrScreenGridCell(
    item: QrTypeUI,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigher),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .weight(1f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            Box(
                modifier = Modifier
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


            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(item.textRes),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@MultiDevicePreview
@Composable
private fun CreateQrScreenPreview() {
    QRCraftTheme {
        CreateQrScreen()
    }
}