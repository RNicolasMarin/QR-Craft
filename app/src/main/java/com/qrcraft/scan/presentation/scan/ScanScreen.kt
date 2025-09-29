package com.qrcraft.scan.presentation.scan

import android.Manifest
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.qrcraft.R
import com.qrcraft.core.presentation.components.BaseComponentAction.*
import com.qrcraft.core.presentation.components.BottomNavigationBarOption.*
import com.qrcraft.core.presentation.components.InfoToShow
import com.qrcraft.core.presentation.components.InfoToShow.*
import com.qrcraft.core.presentation.components.QrCraftBaseComponent
import com.qrcraft.core.presentation.components.SnackBarType.*
import com.qrcraft.core.presentation.designsystem.DimensScan
import com.qrcraft.core.presentation.designsystem.ObserveAsEvents
import com.qrcraft.core.presentation.designsystem.OnOverlay
import com.qrcraft.core.presentation.designsystem.ScreenConfiguration
import com.qrcraft.core.presentation.designsystem.ScreenConfiguration.*
import com.qrcraft.core.presentation.designsystem.SurfaceHigher
import com.qrcraft.core.presentation.designsystem.dimen
import com.qrcraft.core.presentation.designsystem.screenConfiguration
import com.qrcraft.core.presentation.designsystem.statusBarHeight
import com.qrcraft.scan.presentation.scan.ScanAction.*
import com.qrcraft.scan.presentation.scan.ScanAction.ScannerQrNotFound
import com.qrcraft.scan.presentation.scan.ScanEvent.*
import com.qrcraft.scan.presentation.scan.ScanInfoToShow.*
import com.qrcraft.scan.presentation.util.hasCameraPermission
import com.qrcraft.scan.presentation.util.openAppSettings
import com.qrcraft.scan.presentation.util.yuvToRgb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import kotlin.Int
import kotlin.coroutines.resumeWithException
import kotlin.math.min

@Composable
fun ScanScreenRoot(
    onScanResultSuccess: (Int) -> Unit,
    onCreateQr: () -> Unit,
    onScanHistory: () -> Unit,
    viewModel: ScanViewModel = koinViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.onAction(UpdateGrantedInitially(context.hasCameraPermission()))
    }

    val activity = context as Activity

    val cameraLauncher = rememberLauncherForActivityResult(
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

    val scanner = remember {
        BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE,
                    Barcode.FORMAT_AZTEC
                )
                .build()
        )
    }

    var imagePickedResult by remember { mutableStateOf<ImagePickedResult>(ImagePickedResult.Idle) }

    LaunchedEffect(imagePickedResult) {
        val uriResult = imagePickedResult
        when (uriResult) {
            ImagePickedResult.Idle -> Unit
            ImagePickedResult.Error -> {
                viewModel.onAction(ScannerQrNotFound)
                imagePickedResult = ImagePickedResult.Idle
            }
            is ImagePickedResult.Success -> {
                viewModel.onAction(ScannerLoading)
                withContext(Dispatchers.Default) {
                    val bitmap = uriToBitmap(context, uriResult.uri)
                    if (bitmap == null) {
                        viewModel.onAction(ScannerQrNotFound)
                        return@withContext
                    }

                    scanner.detectQrCodes(
                        bitmap = bitmap,
                        callErrorOnNoValue = true,
                        onAction = {
                            when (it) {
                                ScannerQrNotFound -> {
                                    imagePickedResult = ImagePickedResult.Idle
                                }
                                else -> Unit
                            }
                            viewModel.onAction(it)
                        }
                    )
                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imagePickedResult = if (uri != null) ImagePickedResult.Success(uri) else ImagePickedResult.Error
    }

    var snackBarMessage by remember { mutableStateOf<String?>(null) }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            CloseApp -> activity.finish()

            RequestPermissionToSystem -> cameraLauncher.launch(Manifest.permission.CAMERA)

            OpenAppSettings -> context.openAppSettings()

            ShowPermissionGrantedSnackBar -> {
                snackBarMessage = PERMISSION_GRANTED.text
            }

            is GoToScanResult -> onScanResultSuccess(event.qrCodeId)
        }
    }

    ScanScreen(
        scanner = scanner,
        snackBarMessage = snackBarMessage,
        state = viewModel.state,
        isScanning = viewModel.state::isScanning,
        onAction = { action ->
            when (action) {
                OnCreateQr -> onCreateQr()
                OnScanHistory -> onScanHistory()
                PickImage -> {
                    launcher.launch("image/*")
                }
                ClearMessageSnackBar -> {
                    snackBarMessage = null
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun ScanScreen(
    scanner: BarcodeScanner,
    snackBarMessage: String?,
    state: ScanState,
    isScanning: () -> Boolean,
    onAction: (ScanAction) -> Unit,
    screenConfiguration: ScreenConfiguration = MaterialTheme.screenConfiguration,
) {
    QrCraftBaseComponent(
        color = Color.Transparent,
        snackBarMessage = snackBarMessage,
        selectedOption = NONE_SELECTED,
        infoToShow = when (state.infoToShow) {
            NONE -> None
            REQUEST_PERMISSION -> InfoToShow.RequestPermission(
                title = R.string.camera_permission_dialog_title,
                text = R.string.camera_permission_dialog_message,
                confirmButton = R.string.camera_permission_dialog_grant,
                dismissButton = R.string.camera_permission_dialog_close,
            )
            LOADING -> Loading
            NO_QR_FOUND -> Error
        },
        onAction = {
            when (it) {
                BottomNavigationBarOnCreate -> onAction(OnCreateQr)
                BottomNavigationBarOnHistory -> onAction(OnScanHistory)
                DialogOnClosed -> onAction(CustomDialogClosed)
                DialogOnConfirm -> onAction(RequestPermission)
                DialogOnErrorClosed -> onAction(ScannerRestartRunning)
                SnackBarClearMessage -> onAction(ClearMessageSnackBar)
                else -> Unit
            }
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            //Camera
            QRCodeScanner(
                scanner = scanner,
                state = state,
                isScanning = isScanning,
                modifier = Modifier.fillMaxSize(),
                onAction = onAction
            )

            //Flashlight, Image picker, Frame, label
            val backModifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))

            if (screenConfiguration == LANDSCAPE) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = backModifier
                        .padding(
                            top = statusBarHeight() + 8.dp,
                            start = 24.dp,
                            end = 24.dp,
                            bottom = 30.dp
                        )
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        if (state.infoToShow == NONE) {
                            TopIconButton(
                                background = SurfaceHigher,
                                iconRes = R.drawable.ic_image_picker,
                                onClick = {
                                    onAction(PickImage)
                                }
                            )
                            TopIconButton(
                                background = if (state.isFlashOn) MaterialTheme.colorScheme.primary else SurfaceHigher,
                                iconRes = if (state.isFlashOn) R.drawable.ic_flashlight_off else R.drawable.ic_flashlight_on,
                                onClick = {
                                    onAction(TryToggleFlashlight(true))
                                }
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        Text(
                            text = stringResource(R.string.point_your_camera),
                            style = MaterialTheme.typography.titleSmall,
                            color = OnOverlay,
                        )

                        Spacer(Modifier.height(24.dp))

                        if (state.infoToShow != LOADING) {
                            ScanFrame(
                                modifier = Modifier
                                    .weight(1f).aspectRatio(1f)
                            )
                        }
                    }
                    Box(modifier = Modifier.fillMaxHeight())
                }
            } else {
                Column(
                    modifier = backModifier
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(
                                top = statusBarHeight() + 20.dp,
                                start = 24.dp,
                                end = 24.dp,
                                bottom = 30.dp
                            )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (state.infoToShow == NONE) {
                                TopIconButton(
                                    background = if (state.isFlashOn) MaterialTheme.colorScheme.primary else SurfaceHigher,
                                    iconRes = if (state.isFlashOn) R.drawable.ic_flashlight_off else R.drawable.ic_flashlight_on,
                                    onClick = {
                                        onAction(TryToggleFlashlight(true))
                                    }
                                )
                                TopIconButton(
                                    background = SurfaceHigher,
                                    iconRes = R.drawable.ic_image_picker,
                                    onClick = {
                                        onAction(PickImage)
                                    }
                                )
                            }
                        }
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
                        if (state.infoToShow != LOADING) {
                            ScanFrame(
                                modifier = Modifier.fillMaxHeight().aspectRatio(1f)
                            )
                        }
                    }

                    Box(modifier = Modifier.weight(1f).fillMaxWidth())
                }
            }
        }
    }
}

@Composable
fun TopIconButton(
    background: Color,
    iconRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    dimens: DimensScan = MaterialTheme.dimen.scan
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(dimens.iconButtonSize)
            .background(background, shape = RoundedCornerShape(100.dp))
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            tint = MaterialTheme.colorScheme.onSurface,
            contentDescription = "Icon",
            modifier = Modifier
                .size(16.dp)
        )
    }
}

const val scanInterval = 1000L

@OptIn(ExperimentalGetImage::class)
@Composable
fun QRCodeScanner(
    modifier: Modifier = Modifier,
    scanner: BarcodeScanner,
    state: ScanState,
    isScanning: () -> Boolean,
    onAction: (ScanAction) -> Unit,
) {

    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    var lastTime by remember { mutableLongStateOf(0L) }
    var camera by remember { mutableStateOf<Camera?>(null) }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.addObserver(
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    onAction(ToggleFlashlight(false))
                }
            }
        )
    }


    val orientation = LocalConfiguration.current.orientation
    when (state.permissionGranted) {
        true -> {
            key(orientation) {
                AndroidView(
                    factory = { ctx ->
                        val previewView = PreviewView(ctx).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }

                        val preview = Preview.Builder().build().also {
                            it.surfaceProvider = previewView.surfaceProvider
                        }

                        val analysisUseCase = ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()

                        analysisUseCase.setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                            if (!isScanning()) {
                                imageProxy.close()
                                return@setAnalyzer
                            }

                            val now = System.currentTimeMillis()
                            if (now - lastTime < scanInterval) {
                                imageProxy.close()
                                return@setAnalyzer
                            }
                            lastTime = now

                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                // Camera image dimensions
                                val imageWidth = mediaImage.width
                                val imageHeight = mediaImage.height

                                // Square side (1/3 of screen height) and centered in camera image
                                val side = imageHeight / 3
                                val left = (imageWidth - side) / 2
                                val top = (imageHeight - side) / 2

                                // Convert MediaImage to bitmap
                                val bitmap = mediaImage.yuvToRgb()

                                Log.d("ScanningInfo", "imageHeight: $imageHeight, imageWidth: $imageWidth, left: $left, top: $top, side: $side, imageProxy.imageInfo.rotationDegrees: ${imageProxy.imageInfo.rotationDegrees}")

                                // Crop center square
                                val croppedBitmap = Bitmap.createBitmap(bitmap, left, top, side, side)

                                scanner.detectQrCodes(
                                    bitmap = croppedBitmap,
                                    rotationDegree = imageProxy.imageInfo.rotationDegrees,
                                    onAction = onAction,
                                    onComplete = {
                                        imageProxy.close()
                                    }
                                )
                            } else {
                                imageProxy.close()
                                onAction(ScannerQrNotFound)
                            }
                        }

                        cameraProviderFuture.get().unbindAll()
                        camera = cameraProviderFuture.get().bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            analysisUseCase
                        )

                        camera?.checkIfCanTurnOnOfOff(isTrying = state.isCheckingAvailable, isOn = state.isFlashOn, onAction = onAction)

                        previewView
                    },
                    update = {
                        camera?.checkIfCanTurnOnOfOff(isTrying = state.isCheckingAvailable, isOn = state.isFlashOn, onAction = onAction)
                    },
                    modifier = modifier
                )
            }

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

fun BarcodeScanner.detectQrCodes(
    bitmap: Bitmap,
    rotationDegree: Int = 0,
    onAction: (ScanAction) -> Unit,
    callErrorOnNoValue: Boolean = false,
    onComplete: () -> Unit = {}
) {
    val image = InputImage.fromBitmap(bitmap, rotationDegree)

    process(image)
        .addOnSuccessListener { barcodes ->
            if (barcodes.isNotEmpty()) {
                onAction(ScannerLoading)
            }

            barcodes.firstOrNull()?.rawValue?.let { value ->
                onAction(ScannerSuccess(value))
            } ?: run {
                if (callErrorOnNoValue) {
                    onAction(ScannerQrNotFound)
                }
            }
        }
        .addOnCanceledListener {
            onAction(ScannerQrNotFound)
        }
        .addOnFailureListener {
            onAction(ScannerQrNotFound)
        }
        .addOnCompleteListener {
            onComplete()
        }
}

fun Camera.checkIfCanTurnOnOfOff(
    isTrying: Boolean?,
    isOn: Boolean,
    onAction: (ScanAction) -> Unit
) {
    when (isTrying) {
        true -> {
            val available = cameraInfo.hasFlashUnit()
            if (available) {
                onAction(ToggleFlashlight(!isOn))
            } else {
                onAction(TryToggleFlashlight(false))
            }
        }
        false -> {
            cameraControl.enableTorch(isOn)
        }
        null -> Unit
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

suspend fun uriToBitmap(context: android.content.Context, uri: Uri): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

sealed class ImagePickedResult {
    data object Idle: ImagePickedResult()
    data object Error: ImagePickedResult()
    data class Success(
        val uri: Uri
    ): ImagePickedResult()
}

@kotlin.OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.awaitResult(): T = suspendCancellableCoroutine { cont ->
    addOnSuccessListener { result -> cont.resume(result, onCancellation = { cont.cancel() }) }
    addOnFailureListener { exc -> cont.resumeWithException(exc) }
    addOnCanceledListener { cont.cancel() }
}