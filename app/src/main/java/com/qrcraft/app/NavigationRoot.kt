package com.qrcraft.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.qrcraft.app.Screen.*
import com.qrcraft.create.presentation.create_qr.CreateQrScreenRoot
import com.qrcraft.scan.presentation.scan.ScanScreenRoot
import com.qrcraft.scan.presentation.scan_result.ScanResultScreenRoot

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
            ScanResultScreenRoot(
                qrContent = args.qrContent,
                onBackToScan = {
                    navController.navigate(Scan) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable<CreateQR> {
            CreateQrScreenRoot(

            )
        }
    }
}