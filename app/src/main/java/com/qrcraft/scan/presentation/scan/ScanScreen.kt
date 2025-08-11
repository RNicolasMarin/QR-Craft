package com.qrcraft.scan.presentation.scan

import android.Manifest
import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.qrcraft.R
import com.qrcraft.core.presentation.designsystem.ObserveAsEvents
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.QRCraftDialog
import com.qrcraft.core.presentation.designsystem.QRCraftSnackBar
import com.qrcraft.scan.presentation.scan.ScanAction.CustomDialogClosed
import com.qrcraft.scan.presentation.scan.ScanAction.RequestPermission
import com.qrcraft.scan.presentation.scan.ScanAction.UpdateAfterPermissionRequested
import com.qrcraft.scan.presentation.scan.ScanAction.UpdateGrantedInitially
import com.qrcraft.scan.presentation.scan.ScanEvent.*
import com.qrcraft.scan.presentation.util.hasCameraPermission
import com.qrcraft.scan.presentation.util.openAppSettings
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.min

@Composable
fun ScanScreenRoot(
    viewModel: ScanViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val activity = context as Activity

    LaunchedEffect(true) {
        viewModel.onAction(UpdateGrantedInitially(context.hasCameraPermission()))
    }

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        Log.d("QRCraft Permission", "isGranted: $isGranted")

        viewModel.onAction(
            UpdateAfterPermissionRequested(
                isGranted = isGranted,
                canRequestAgain = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)
            )
        )
    }

    val grantedMessage = stringResource(R.string.camera_permission_snack_bar_granted)

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            CloseApp -> activity.finish()

            RequestPermissionToSystem -> launcher.launch(Manifest.permission.CAMERA)

            OpenAppSettings -> context.openAppSettings()

            ShowPermissionGrantedSnackBar -> {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = grantedMessage,
                    )
                }
            }
        }
    }

    ScanScreen(
        snackBarHostState = snackBarHostState,
        state = viewModel.state,
        onAction = { action ->
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ScanScreen(
    snackBarHostState: SnackbarHostState,
    state: ScanState,
    onAction: (ScanAction) -> Unit
) {
    var qrContent by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        //Camera
        QRCodeScanner(
            state = state,
            modifier = Modifier.fillMaxSize()
        ) { scannedValue ->
            qrContent = scannedValue
        }

        //Frame, label, snackbar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 30.dp)
            ) {
                Text(
                    text = stringResource(R.string.point_your_camera),
                    style = MaterialTheme.typography.titleSmall,
                    color = OnOverlay,
                )
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ScanFrame(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                MyCustomSnackBarHost(
                    hostState = snackBarHostState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 96.dp)
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            //dialog and loading
            if (state.showPermissionDialog) {
                QRCraftDialog(
                    title = R.string.camera_permission_dialog_title,
                    text = R.string.camera_permission_dialog_message,
                    confirmButton = R.string.camera_permission_dialog_grant,
                    dismissButton = R.string.camera_permission_dialog_close,
                    onDismissRequest = {
                        onAction(CustomDialogClosed)
                    },
                    onConfirmButtonClick = {
                        onAction(RequestPermission)
                    },
                    onDismissButtonClick = {
                        onAction(CustomDialogClosed)
                    }
                )
            }
        }
    }
}

@Composable
fun MyCustomSnackBarHost(
    hostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
    ) { data ->
        QRCraftSnackBar(
            message = data.visuals.message
        )
    }
}

@OptIn(ExperimentalGetImage::class)
@Composable
fun QRCodeScanner(
    modifier: Modifier = Modifier,
    state: ScanState,
    onQRCodeScanned: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    when (state.permissionGranted) {
        true -> {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }

                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                    val barcodeScanner = BarcodeScanning.getClient(
                        BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_AZTEC
                            )
                            .build()
                    )

                    val analysisUseCase = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()

                    analysisUseCase.setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                            barcodeScanner.process(image)
                                .addOnSuccessListener { barcodes ->
                                    for (barcode in barcodes) {
                                        barcode.rawValue?.let { value ->
                                            onQRCodeScanned(value)
                                        }
                                    }
                                }
                                .addOnCompleteListener {
                                    imageProxy.close()
                                }
                        } else {
                            imageProxy.close()
                        }
                    }

                    cameraProviderFuture.get().bindToLifecycle(
                        lifecycleOwner,
                        CameraSelector.DEFAULT_BACK_CAMERA,
                        preview,
                        analysisUseCase
                    )

                    previewView
                },
                modifier = modifier
            )
        }
        false -> {
            Box(
                modifier = modifier,
                contentAlignment = Alignment.Center
            ) {
                Text("Camera permission is required")
            }
        }
        null -> {}
    }
}

@Composable
fun ScanFrame(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    borderWidth: Dp = 4.dp
) {
    val density = LocalDensity.current
    val cornerRadiusPx = with(density) { cornerRadius.toPx() }
    val borderWidthPx = with(density) { borderWidth.toPx() }

    Canvas(modifier = modifier) {
        val roundRectSize = size.copy(
            width = size.width - borderWidthPx,
            height = size.height - borderWidthPx
        )
        val roundRectCornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)

        //Center transparent
        drawRoundRect(
            color = Color.Transparent,
            size = roundRectSize,
            cornerRadius = roundRectCornerRadius,
            blendMode = BlendMode.Clear
        )

        //Yellow border
        drawRoundRect(
            color = Color.Yellow,
            size = roundRectSize,
            cornerRadius = roundRectCornerRadius,
            style = Stroke(width = borderWidthPx)
        )

        val fifth = min(size.width, size.height) / 5
        val removedAreaSize = fifth * 3

        //Semi-transparent Rect to Remove Yellow Top
        drawScanFrameRemoveSomeYellowPart(
            x = fifth,
            y = 0 - borderWidthPx / 2,
            rectWidth = removedAreaSize,
            rectHeight = borderWidthPx,
        )

        //Semi-transparent Rect to Remove Yellow Bottom
        drawScanFrameRemoveSomeYellowPart(
            x = fifth,
            y = size.height - borderWidthPx * 1.5f,
            rectWidth = removedAreaSize,
            rectHeight = borderWidthPx,
        )

        //Semi-transparent Rect to Remove Yellow Left
        drawScanFrameRemoveSomeYellowPart(
            x = 0 - borderWidthPx / 2,
            y = fifth,
            rectWidth = borderWidthPx,
            rectHeight = removedAreaSize,
        )

        //Semi-transparent Rect to Remove Yellow Right
        drawScanFrameRemoveSomeYellowPart(
            x = size.height - borderWidthPx * 1.5f,
            y = fifth,
            rectWidth = borderWidthPx,
            rectHeight = removedAreaSize,
        )
    }
}

private fun DrawScope.drawScanFrameRemoveSomeYellowPart(
    x: Float,
    y: Float,
    rectWidth: Float,
    rectHeight: Float,
) {
    val semiTransparentColor = Color.Black.copy(alpha = 0.5f)
    val topLeft = Offset(x,  y)
    val rectSize = size.copy(
        width = rectWidth,
        height = rectHeight
    )
    drawRect(
        color = semiTransparentColor,
        topLeft = topLeft,
        size = rectSize,
        blendMode = BlendMode.Clear
    )
    drawRect(
        color = semiTransparentColor,
        topLeft = topLeft,
        size = rectSize,
    )
}