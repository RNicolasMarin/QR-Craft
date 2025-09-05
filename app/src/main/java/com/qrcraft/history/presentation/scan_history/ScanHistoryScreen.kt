package com.qrcraft.history.presentation.scan_history

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.QRCraftBottomNavigationBar
import com.qrcraft.core.presentation.components.QRCraftTopBar
import com.qrcraft.core.presentation.components.QrCodeTypeIcon
import com.qrcraft.core.presentation.designsystem.Dimens
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.OnSurfaceDisabled
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.SurfaceHigher
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.core.presentation.formatTimestamp
import com.qrcraft.create.presentation.create_qr.QrTypeUI
import com.qrcraft.history.presentation.scan_history.ScanHistoryAction.*
import com.qrcraft.scan.domain.QrCode
import com.qrcraft.scan.domain.QrCodeType.*
import com.qrcraft.scan.domain.ScannedOrGenerated.*
import com.qrcraft.scan.presentation.util.getFormattedContentHistory
import com.qrcraft.scan.presentation.util.getFormattedContentResultPreview
import com.qrcraft.scan.presentation.util.shareContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScanHistoryScreenRoot(
    onGoToPreview: (Int) -> Unit,
    viewModel: ScanHistoryViewModel = koinViewModel()
) {
    val context = LocalContext.current

    ScanHistoryScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is GoToPreview -> onGoToPreview(action.qrCodeId)
                is ShareContent -> {
                    context.shareContent(action.qrContent)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanHistoryScreen(
    state: ScanHistoryState,
    onAction: (ScanHistoryAction) -> Unit,
    dimens: DimensTopBar = MaterialTheme.dimen.topBar
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
        ) {
            QRCraftTopBar(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimens.paddingStart, end = dimens.paddingEnd),
                titleRes = R.string.scan_history_title,
            )

            ScanHistoryTabsAndContent(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }

        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = dimens.paddingStart, end = dimens.paddingEnd)
        ) {
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFEDF2F5).copy(alpha = 0f),  // top transparent
                            Color(0xFFEDF2F5)                    // bottom solid
                        )
                    )
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = dimens.paddingStart, end = dimens.paddingEnd)
        ) {
            Spacer(modifier = Modifier
                .weight(8f)
            )

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                QRCraftBottomNavigationBar(
                    isOnHistory = true,
                    onCreate = { },
                    onHistory = { }
                )
            }
        }

        if (state.selectedQrCode != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                ModalBottomSheet(
                    onDismissRequest = { onAction(CloseMoreOptions) },
                    dragHandle = null,
                    containerColor = SurfaceHigher,
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = dimens.paddingStart, end = dimens.paddingEnd, top = 8.dp, bottom = 8.dp)
                    ) {
                        val qrContent = state.selectedQrCode?.getFormattedContentResultPreview()
                        ScanHistoryBottomSheetOption(
                            iconRes = R.drawable.ic_share,
                            textRes = R.string.scan_history_options_share,
                            color = MaterialTheme.colorScheme.onSurface,
                            onClick = {
                                qrContent?.let {
                                    onAction(ShareContent(qrContent))
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ScanHistoryBottomSheetOption(
                            iconRes = R.drawable.ic_delete,
                            textRes = R.string.scan_history_options_delete,
                            color = MaterialTheme.colorScheme.error,
                            onClick = { onAction(DeleteQrCode) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScanHistoryBottomSheetOption(
    @DrawableRes iconRes: Int,
    @StringRes textRes: Int,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            tint = color,
            contentDescription = stringResource(textRes),
            modifier = Modifier
                .size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(textRes),
            style = MaterialTheme.typography.labelLarge,
            color = color,
        )
    }
}

@Composable
fun ScanHistoryTabsAndContent(
    state: ScanHistoryState,
    onAction: (ScanHistoryAction) -> Unit,
    modifier: Modifier = Modifier,
    dimens: Dimens = MaterialTheme.dimen,
) {
    Column(
        modifier = modifier
    ) {
        val tabs = listOf(stringResource(R.string.scan_history_tab_scanned), stringResource(R.string.scan_history_tab_generated))

        TabRow(
            selectedTabIndex = state.scannedOrGenerated.typeValue,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[state.scannedOrGenerated.typeValue])
                        .height(2.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSurface,
                            shape = RoundedCornerShape(
                                topStart = 100.dp,
                                topEnd = 100.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp,
                            )
                        ),
                    height = 2.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            divider = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = dimens.topBar.paddingStart, end = dimens.topBar.paddingEnd)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = state.scannedOrGenerated.typeValue == index,
                    onClick = {
                        onAction(ChangeScannedOrGeneratedCode)
                    },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (state.scannedOrGenerated.typeValue == index) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                    }
                )
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline,
            thickness = 1.dp
        )

        Spacer(modifier = Modifier.height(12.dp))

        val listStateScanned = rememberLazyListState()
        val listStateGenerated = rememberLazyListState()

        Crossfade(targetState = state.scannedOrGenerated) { tab ->
            when (tab) {
                SCANNED -> ScanHistoryList(
                    qrCodes = state.qrCodesScanned,
                    onAction = onAction,
                    listState = listStateScanned
                )
                GENERATED -> ScanHistoryList(
                    qrCodes = state.qrCodesGenerated,
                    onAction = onAction,
                    listState = listStateGenerated
                )
            }
        }
    }
}

@Composable
fun ScanHistoryList(
    qrCodes: List<QrCode>,
    onAction: (ScanHistoryAction) -> Unit,
    dimens: Dimens = MaterialTheme.dimen,
    listState: LazyListState
) {
    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = dimens.topBar.paddingStart, end = dimens.topBar.paddingEnd)
    ) {
        items(
            items = qrCodes,
            key = { qrCode -> qrCode.id }
        ) { qrCode ->
            ScanHistoryItem(
                qrCode = qrCode,
                onAction = onAction,
                modifier = Modifier
            )
        }
        item {
            Spacer(modifier = Modifier.height(dimens.bottomBar.scanOuter))
        }
    }
}

@Composable
fun ScanHistoryItem(
    qrCode: QrCode,
    onAction: (ScanHistoryAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiType = QrTypeUI.entries.first { qrCode.type.typeId == it.qrCodeTypeId}
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceHigher),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onAction(GoToPreview(qrCode.id))
                },
                onLongClick = {
                    onAction(OpenMoreOptions(qrCode))
                }
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            QrCodeTypeIcon(
                item = uiType,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val title = qrCode.title.ifBlank { stringResource(uiType.textRes) }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val content = qrCode.getFormattedContentHistory()

                    Text(
                        text = content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                    )

                    if (qrCode.type is Contact || qrCode.type is Wifi) {
                        Text(
                            text = stringResource(R.string.scan_history_empty_field),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = formatTimestamp(qrCode.createdAt),
                    style = MaterialTheme.typography.bodySmall,
                    color = OnSurfaceDisabled,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@MultiDevicePreview
@Composable
private fun ScanHistoryScreenPreview() {
    QRCraftTheme {
        ScanHistoryScreen(
            state = ScanHistoryState(),
            onAction = {

            }
        )
    }
}