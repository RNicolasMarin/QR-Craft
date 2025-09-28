package com.qrcraft.create.presentation.create_qr

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.BaseComponentAction.*
import com.qrcraft.core.presentation.components.BottomNavigationBarOption.*
import com.qrcraft.core.presentation.components.QRCraftTopBarConfig
import com.qrcraft.core.presentation.components.QrCraftBaseComponent
import com.qrcraft.core.presentation.components.QrCodeTypeIcon
import com.qrcraft.core.presentation.designsystem.DimensCreateQr
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.SurfaceHigher
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.create.presentation.create_qr.CreateQrAction.*

@Composable
fun CreateQrScreenRoot(
    onDataEntry: (Int) -> Unit,
    onHistory: () -> Unit,
    onScanQr: () -> Unit,
) {
    CreateQrScreen(
        onAction = { action ->
            when (action) {
                OnHistory -> onHistory()
                OnScanQr -> onScanQr()
                is OnDataEntry -> {
                    onDataEntry(action.qrTypeOrdinal)
                }
            }
        }
    )
}

@Composable
fun CreateQrScreen(
    onAction: (CreateQrAction) -> Unit,
    dimens: DimensTopBar = MaterialTheme.dimen.topBar
) {
    QrCraftBaseComponent(
        color = MaterialTheme.colorScheme.surface,
        topBarConfig = QRCraftTopBarConfig(
            titleRes = R.string.create_qr_title,
            color = MaterialTheme.colorScheme.onSurface,
            backIconRes = null
        ),
        selectedOption = CREATE,
        onAction = {
            when (it) {
                BottomNavigationBarOnHistory -> onAction(OnHistory)
                BottomNavigationBarOnScan -> onAction(OnScanQr)
                else -> Unit
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = dimens.paddingStart, end = dimens.paddingEnd, bottom = 16.dp)
        ) {
            Spacer(Modifier.height(2.dp))

            CreateQrScreenGrid(
                onAction = onAction
            )
        }
    }
}

@Composable
fun CreateQrScreenGrid(
    onAction: (CreateQrAction) -> Unit,
    modifier: Modifier = Modifier,
    dimens: DimensCreateQr = MaterialTheme.dimen.createQr
) {
    val items = QrTypeUI.entries.toList()

    val listState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(dimens.columnsAmount),
        state = listState,
        modifier = modifier.fillMaxWidth()
            .padding(start = dimens.startPadding, end = dimens.endPadding),
        contentPadding = PaddingValues(0.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = items,
            key = { qrType -> qrType.name }
        ) { qrType ->
            CreateQrScreenGridCell(
                item = qrType,
                onAction = onAction
            )
        }
        item {
            Spacer(modifier = Modifier.height(dimens.bottomSpace))
        }
    }
}

@Composable
fun CreateQrScreenGridCell(
    item: QrTypeUI,
    onAction: (CreateQrAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigher),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .clickable(onClick = {
                onAction(OnDataEntry(item.ordinal))
            })
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            QrCodeTypeIcon(
                item = item,
            )

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
        CreateQrScreen(
            onAction = {}
        )
    }
}