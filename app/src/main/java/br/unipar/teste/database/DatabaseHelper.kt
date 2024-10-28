package br.unipar.teste.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_DATABASE ){

    companion object {
        private const val DATABASE_NAME = "COMPRAS_UNIPAR.db"
        private const val VERSION_DATABASE = 1
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = ("CREATE TABLE items (" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " description TEXT, " +
                " quantity INTEGER )")

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS items")
        onCreate(db)
    }
}