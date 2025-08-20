package com.qrcraft.create.presentation.di

import com.qrcraft.create.domain.DataEntryValidator
import com.qrcraft.create.presentation.data_entry.DataEntryViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val createViewModelModule = module {
    viewModelOf(::DataEntryViewModel)
    singleOf(::DataEntryValidator)
}