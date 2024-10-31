package br.unipar.teste.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.unipar.exemplobancodedados.entity.Item

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION_DATABASE ){

    companion object {
        private const val DATABASE_NAME = "COMPRAS_UNIPAR.db"
        private const val VERSION_DATABASE = 1
        private const val TABLE_NAME = "items"

        private const val COLUMN_ID = "id"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_QUANTITY = "quantity"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createTable = ("CREATE TABLE ${TABLE_NAME} (" +
                " ${COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " ${COLUMN_DESCRIPTION} TEXT, " +
                " ${COLUMN_QUANTITY} INTEGER )")

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${TABLE_NAME}")
        onCreate(db)
    }

    fun saveItem(description: String, quantity: Int) {
        val db = this.writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_QUANTITY, quantity)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getListItems(): List<Item>{

        val itemList = mutableListOf<Item>()
        val querySelect = "SELECT * FROM ${TABLE_NAME}"
        val db = this.readableDatabase

        val cursor = db.rawQuery(querySelect, null)

        do {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))

            val item = Item(id, description, quantity)

        } while (cursor.moveToNext())

        return itemList
    }

    fun deleteItem(id: Int){
        //usamos para insert, deelete e update
        val db = this.writableDatabase

        db.delete(TABLE_NAME, " $COLUMN_ID = ? ", arrayOf(id.toString()))
        db.close()
    }
}
