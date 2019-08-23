package com.religion76.mvpkotlin.data.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Created by SunChao on 2019-08-23.
 */

object Migrations{

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE user ADD COLUMN avatar TEXT ")
        }
    }

    val ALL_MIGRATONS = arrayOf(MIGRATION_1_2)

}