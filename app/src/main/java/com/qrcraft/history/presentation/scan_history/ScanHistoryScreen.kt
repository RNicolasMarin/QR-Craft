package com.qrcraft.history.presentation.scan_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.QRCraftBottomNavigationBar
import com.qrcraft.core.presentation.components.QRCraftTopBar
import com.qrcraft.core.presentation.designsystem.DimensTopBar
import com.qrcraft.core.presentation.designsystem.MultiDevicePreview
import com.qrcraft.core.presentation.designsystem.QRCraftTheme
import com.qrcraft.core.presentation.designsystem.dimen

@Composable
fun ScanHistoryScreenRoot() {
    ScanHistoryScreen()
}

@Composable
fun ScanHistoryScreen(
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
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
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
    }
}

@Composable
fun ScanHistoryTabsAndContent(
    modifier: Modifier = Modifier,
    dimens: DimensTopBar = MaterialTheme.dimen.topBar
) {
    Column(
        modifier = modifier
    ) {
        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val tabs = listOf(stringResource(R.string.scan_history_tab_scanned), stringResource(R.string.scan_history_tab_generated))

        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex])
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
                .padding(start = dimens.paddingStart, end = dimens.paddingEnd)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (selectedTabIndex == index) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
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
    }
}

@MultiDevicePreview
@Composable
private fun ScanHistoryScreenPreview() {
    QRCraftTheme {
        ScanHistoryScreen()
    }
}