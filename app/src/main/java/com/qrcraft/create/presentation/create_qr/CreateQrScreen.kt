package com.qrcraft.create.presentation.create_qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.qrcraft.R
import com.qrcraft.core.presentation.components.QRCraftBottomNavigationBar
import com.qrcraft.core.presentation.components.QRCraftTopBar
import com.qrcraft.core.presentation.designsystem.DimensTopBar
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(start = dimens.paddingStart, end = dimens.paddingEnd, bottom = 16.dp)
                .verticalScroll(scrollState),
        ) {
            QRCraftTopBar(
                color = MaterialTheme.colorScheme.onSurface,
                titleRes = R.string.create_qr_title,
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Spacer(
                modifier = Modifier.weight(8f)
            )
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


}