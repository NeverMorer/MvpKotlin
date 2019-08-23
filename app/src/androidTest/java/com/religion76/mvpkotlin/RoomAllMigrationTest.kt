package com.religion76.mvpkotlin

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.religion76.mvpkotlin.data.db.AppDatabase
import com.religion76.mvpkotlin.data.db.Migrations
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by SunChao on 2019-08-23.
 */
@RunWith(AndroidJUnit4::class)
class RoomAllMigrationTest {
    private val TEST_DB_NAME = "migration_test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrateAll() {
        helper.createDatabase(TEST_DB_NAME, 1).apply {
            close()
        }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().getTargetContext(),
            AppDatabase::class.java,
                    TEST_DB_NAME
        ).addMigrations(*Migrations.ALL_MIGRATONS).build().apply {
            getOpenHelper().getWritableDatabase()
            close()
        }

    }
}