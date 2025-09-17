package com.qrcraft.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.qrcraft.core.data.local.daos.QrCodeDao
import com.qrcraft.core.data.local.entities.QrCodeEntity

@Database(
    entities = [QrCodeEntity::class],
    version = 2,
    exportSchema = false
)
abstract class QRCraftDatabase: RoomDatabase() {

    abstract fun qrCodeDao(): QrCodeDao
    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE qr_codes ADD COLUMN isFavourite INTEGER NOT NULL DEFAULT 0")
            }
        }

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
            )
                .addMigrations(MIGRATION_1_2)
                .build()
    }
}