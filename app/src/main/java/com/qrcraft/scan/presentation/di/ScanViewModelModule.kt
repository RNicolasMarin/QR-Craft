package com.qrcraft.scan.presentation.di

import com.qrcraft.scan.domain.QrTypeDetector
import com.qrcraft.scan.presentation.scan.ScanViewModel
import com.qrcraft.scan.presentation.scan_result_preview.ScanResultPreviewViewModel
import org.koin.core.module.dsl.*
import org.koin.dsl.module

val scanViewModelModule = module {
    viewModelOf(::ScanViewModel)
    viewModelOf(::ScanResultPreviewViewModel)
    singleOf(::QrTypeDetector)
}