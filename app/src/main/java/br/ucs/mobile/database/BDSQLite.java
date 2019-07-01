package br.ucs.mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.ucs.mobile.model.ConteudoLido;

public class BDSQLite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "T3DB";

    //Dragon
    private static final String TABELA_CONTEUDO_LIDO = "conteudo";
    private static final String CONTEUDO_ID = "conteudo_id";
    private static final String CONTEUDO_CODIGO_BARRAS = "conteudo_codigo_barras";
    private static final String CONTEUDO_TIPO = "conteudo_tipo";
    private static final String[] COLUNAS_CONTEUDO = {CONTEUDO_ID, CONTEUDO_CODIGO_BARRAS, CONTEUDO_TIPO};

      public BDSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE  " + TABELA_CONTEUDO_LIDO + "(" +
                CONTEUDO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CONTEUDO_CODIGO_BARRAS + " TEXT," +
                CONTEUDO_TIPO + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CONTEUDO_LIDO);
        this.onCreate(db);
    }

    private boolean checkIfDatabaseExist() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    public void deleteAllRegisters() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABELA_CONTEUDO_LIDO);
    }

    public void addConteudoLido(ConteudoLido conteudoLido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTEUDO_ID, conteudoLido.getConteudoId());
        values.put(CONTEUDO_CODIGO_BARRAS, conteudoLido.getConteudoCodigoBarras());
        values.put(CONTEUDO_TIPO, conteudoLido.getConteudoTipo());
        db.insert(TABELA_CONTEUDO_LIDO, null, values);
        db.close();
    }

    public List<ConteudoLido> getConteudoLido(String conteudoWhere) {
        conteudoWhere = "%" + conteudoWhere + "%";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABELA_CONTEUDO_LIDO,
                COLUNAS_CONTEUDO,
                CONTEUDO_ID+ " like ? or " + CONTEUDO_CODIGO_BARRAS + " like ? or " + CONTEUDO_TIPO + " like ?",
                new String[]{conteudoWhere, conteudoWhere, conteudoWhere},
                null,
                null,
                null,
                null);
        if (cursor == null || cursor.getCount() <= 0) {
            return null;
        } else {
            List<ConteudoLido> shipList = new ArrayList<ConteudoLido>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    shipList.add(cursorToConteudo(cursor));
                    cursor.moveToNext();
                }
            }
            return shipList;
        }
    }

    private ConteudoLido cursorToConteudo(Cursor cursor) {
        ConteudoLido conteudo = new ConteudoLido();
        conteudo.setConteudoId(Integer.parseInt(cursor.getString(0)));
        conteudo.setConteudoCodigoBarras(cursor.getString(1));
        conteudo.setConteudoTipo(cursor.getString(2));
        return conteudo;
    }

    public ArrayList<ConteudoLido> getAllConteudoLido() {
        ArrayList<ConteudoLido> listaShips = new ArrayList<ConteudoLido>();
        String query = "SELECT * FROM " + TABELA_CONTEUDO_LIDO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                ConteudoLido ship = cursorToConteudo(cursor);
                listaShips.add(ship);
            } while (cursor.moveToNext());
        }
        return listaShips;
    }

}
