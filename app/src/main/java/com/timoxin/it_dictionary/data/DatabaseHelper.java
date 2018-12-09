package com.timoxin.it_dictionary.data;
/* Для открытия и подготовки БД в Android используется наследник класса SQLiteOpenHelper.
Создаю наследник этого класса DatabaseHelper, но он будет сильно модифицированный,
так как буду работать с готовой базой данных, а не создавать её с помощью SQL запросов.
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "words.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1; //если приложение обновляется а именно данные БД то и версия инкрементируется
    private static final String WORD_TABLE = "words";
    private static final String WORD_COLUMN = "word";
    private static final String MY_WORDS_TABLE = "my_words";
    private static final String MY_WORD_COLUMN = "my_word";
    private static final String MY_WORD_DESCRIPTION_COLUMN = "my_word_description";

    private SQLiteDatabase mDataBase;
    private final Context mContext;
    private boolean mNeedUpdate = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION); //вызов конструктора суперкласса

        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";

        this.mContext = context;
        // copy database from assets/words.db in local storage app
        copyDataBase();
        //Create and/or open a database.
        this.getReadableDatabase();
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }
    // onCreate вызывается если БД не будет сущетвовать но мы хотим к ней подключиться
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    //onUpgrade - будет вызван в случае, если мы пытаемся подключиться к БД более новой версии, чем существующая
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }
    //get words from table words
    public Cursor getListWords() {
        mDataBase = this.getWritableDatabase();
        Cursor data = mDataBase.rawQuery("SELECT * FROM " + WORD_TABLE + " ORDER BY " + WORD_COLUMN, null);
        return data;
    }

    //get words from table myWords
    public Cursor getListMyWords() {
        mDataBase = this.getWritableDatabase();
        Cursor data = mDataBase.rawQuery("SELECT * FROM " + MY_WORDS_TABLE + " ORDER BY " + MY_WORD_COLUMN, null);
        return data;
    }

    public boolean addWord(String name, String description){
        mDataBase = this.getWritableDatabase();
        ContentValues wordContentValue = new ContentValues();
        wordContentValue.put(MY_WORD_COLUMN, name);
        wordContentValue.put(MY_WORD_DESCRIPTION_COLUMN, description);
        long result = mDataBase.insert(MY_WORDS_TABLE, null, wordContentValue);
        if(result ==- 1){
            return false;
        } else {
            return true;
        }
    }

}
