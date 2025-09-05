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
import com.qrcraft.history.presentation.scan_history.ScanHistoryScreenRoot
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
                onScanResultSuccess = { qrCodeId ->
                    navController.navigate(
                        ScanResult(
                            qrCodeId = qrCodeId
                        )
                    ) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onCreateQr = {
                    navController.navigate(CreateQR)
                },
                onScanHistory = {
                    navController.navigate(ScanHistory)
                }
            )
        }
        composable<ScanResult> {
            val args = it.toRoute<ScanResult>()
            ScanResultPreviewScreenRoot(
                titleRes = R.string.scan_result,
                qrCodeId = args.qrCodeId,
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
                onGoToPreview = { qrCodeId ->
                    navController.navigate(
                        Preview(
                            qrCodeId = qrCodeId
                        )
                    )
                }
            )
        }
        composable<Preview> {
            val args = it.toRoute<Preview>()
            ScanResultPreviewScreenRoot(
                titleRes = R.string.preview,
                qrCodeId = args.qrCodeId,
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
        composable<ScanHistory> {
            ScanHistoryScreenRoot(
                onGoToPreview = { qrCodeId ->
                    navController.navigate(
                        Preview(
                            qrCodeId = qrCodeId
                        )
                    )
                }
            )
        }
    }
}