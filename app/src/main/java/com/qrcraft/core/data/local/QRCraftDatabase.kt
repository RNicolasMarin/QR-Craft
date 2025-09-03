package com.qrcraft.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.qrcraft.core.data.local.daos.QrCodeDao
import com.qrcraft.core.data.local.entities.QrCodeEntity

@Database(
    entities = [QrCodeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QRCraftDatabase: RoomDatabase() {

    abstract fun qrCodeDao(): QrCodeDao
    companion object {
        @Volatile
        private var INSTANCE: QRCraftDatabase? = null

        fun getInstance(context: Context): QRCraftDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                QRCraftDatabase::class.java,
                "QRCraft.db"
            ).build()
    }
}