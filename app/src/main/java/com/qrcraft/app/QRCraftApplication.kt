package com.qrcraft.app

import android.app.Application
import com.qrcraft.core.data.di.coreDataModule
import com.qrcraft.create.presentation.di.createViewModelModule
import com.qrcraft.history.presentation.di.historyViewModelModule
import com.qrcraft.scan.presentation.di.scanViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QRCraftApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@QRCraftApplication)
            modules(
                scanViewModelModule,
                createViewModelModule,
                historyViewModelModule,
                coreDataModule
            )
        }
    }
}