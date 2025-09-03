package com.qrcraft.core.data.di

import com.qrcraft.core.data.repository.QrCodeRepositoryImpl
import com.qrcraft.core.data.local.QRCraftDatabase
import com.qrcraft.core.domain.QrCodeRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreDataModule = module {
    single {
        QRCraftDatabase.getInstance(
            androidContext()
        )
    }
    // Provide DAO
    single { get<QRCraftDatabase>().qrCodeDao() }

    // Bind interface -> implementation
    single<QrCodeRepository> { QrCodeRepositoryImpl(get()) }
}