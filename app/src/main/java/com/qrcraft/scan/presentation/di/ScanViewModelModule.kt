package com.qrcraft.scan.presentation.di

import com.qrcraft.scan.domain.QrTypeDetector
import com.qrcraft.scan.presentation.scan.ScanViewModel
import org.koin.core.module.dsl.*
import org.koin.dsl.module

val scanViewModelModule = module {
    viewModelOf(::ScanViewModel)
    singleOf(::QrTypeDetector)
}