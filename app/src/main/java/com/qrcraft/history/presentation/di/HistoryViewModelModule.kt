package com.qrcraft.history.presentation.di

import com.qrcraft.history.presentation.scan_history.ScanHistoryViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val historyViewModelModule = module {
    viewModelOf(::ScanHistoryViewModel)
}