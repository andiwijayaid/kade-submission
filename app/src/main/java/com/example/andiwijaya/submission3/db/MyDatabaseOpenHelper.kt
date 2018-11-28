package com.example.andiwijaya.submission3.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.andiwijaya.submission3.model.FavoriteMatch
import com.example.andiwijaya.submission3.model.FavoriteTeam
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "Favorite.db", null, 1) {


    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }

            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            FavoriteMatch.TABLE_MATCH_FAVORITE, true,
            FavoriteMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteMatch.MATCH_ID to TEXT + UNIQUE,
            FavoriteMatch.MATCH_FILE_NAME to TEXT,
            FavoriteMatch.MATCH_DATE to TEXT,
            FavoriteMatch.HOME_NAME to TEXT,
            FavoriteMatch.AWAY_NAME to TEXT,
            FavoriteMatch.HOME_SCORE to TEXT,
            FavoriteMatch.AWAY_SCORE to TEXT
        )

        db.createTable(
            FavoriteTeam.TABLE_TEAM_FAVORITE, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatch.TABLE_MATCH_FAVORITE, true)
        db.dropTable(FavoriteTeam.TABLE_TEAM_FAVORITE, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)