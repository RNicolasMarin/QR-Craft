package com.qrcraft.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.qrcraft.R
import com.qrcraft.app.Screen.*
import com.qrcraft.create.presentation.create_qr.CreateQrScreenRoot
import com.qrcraft.create.presentation.data_entry.DataEntryScreenRoot
import com.qrcraft.scan.presentation.scan.ScanScreenRoot
import com.qrcraft.scan.presentation.scan_result_preview.ScanResultPreviewScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Scan
    ) {
        composable<Scan> {
            ScanScreenRoot(
                onScanResultSuccess = { qrContent ->
                    navController.navigate(ScanResult(qrContent)) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onCreateQr = {
                    navController.navigate(CreateQR) {

                    }
                }
            )
        }
        composable<ScanResult> {
            val args = it.toRoute<ScanResult>()
            ScanResultPreviewScreenRoot(
                titleRes = R.string.scan_result,
                qrContent = args.qrContent,
                onBackPressed = {
                    navController.navigate(Scan) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable<CreateQR> {
            CreateQrScreenRoot(
                onDataEntry = { qrTypeOrdinal ->
                    navController.navigate(DataEntry(qrTypeOrdinal))
                }
            )
        }
        composable<DataEntry> {
            val args = it.toRoute<DataEntry>()
            DataEntryScreenRoot(
                qrTypeOrdinal = args.qrTypeOrdinal,
                onBackToCreateQr = {
                    navController.popBackStack()
                },
                onGoToPreview = { content ->
                    navController.navigate(Preview(content))
                }
            )
        }
        composable<Preview> {
            val args = it.toRoute<Preview>()
            ScanResultPreviewScreenRoot(
                titleRes = R.string.preview,
                qrContent = args.qrContent,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}