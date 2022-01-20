package com.example.learn_english.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.learn_english.BuildConfig;
import com.example.learn_english.Object.Exam;
import com.example.learn_english.Object.Topic;
import com.example.learn_english.Object.Vocabulary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class Model extends SQLiteOpenHelper {
    String TAG = "Model";
    String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    String DB_NAME = "db_english";

    Context context;
    SQLiteDatabase db;

    public Model(@Nullable Context context) {
        super(context, "db_english", null, 1);
        this.context = context;

        if (isCopyDB()) {
            db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean isCopyDB() {
        File file = new File(DB_PATH + DB_NAME);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void copyDB() {
        // create folder
        File file = new File(DB_PATH);
        file.mkdir();
        // copy db
        try {
            InputStream inputStream = context.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
        }

        db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public List<Topic> getListTopic() {
        String sql = "SELECT * FROM Chude";
        Cursor cursor = db.rawQuery(sql, null);

        List<Topic> listTopic = new ArrayList<>();
        while (cursor.moveToNext()) {
            listTopic.add(new Topic(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)));
        }

        return listTopic;
    }

    public List<Vocabulary> getListVocabulary(int topicID) {
        String sql = "SELECT * FROM Itemchude WHERE Idchude = " + topicID;
        Cursor cursor = db.rawQuery(sql, null);

        List<Vocabulary> listVocabulary = new ArrayList<>();
        while (cursor.moveToNext()) {
            listVocabulary.add(new Vocabulary(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(5),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(6)));
        }

        return listVocabulary;
    }

    public List<Vocabulary> getListVocabularyByKeyword(String keyword) {
        String sql = "SELECT * FROM Itemchude WHERE Tutienganh LIKE '%" + keyword + "%'" ;
        Cursor cursor = db.rawQuery(sql, null);

        List<Vocabulary> listVocabulary = new ArrayList<>();
        while (cursor.moveToNext()) {
            listVocabulary.add(new Vocabulary(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(5),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(6)));
        }

        return listVocabulary;
    }

    public List<Exam> getListExam(int topicID) {
        String sql = "SELECT * FROM test WHERE baiTest = " +  topicID  + " ORDER BY RANDOM() LIMIT 5";
        Cursor cursor = db.rawQuery(sql, null);

        List<Exam> listExam = new ArrayList<>();
        while (cursor.moveToNext()) {
            listExam.add(new Exam(cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    "x"));
        }

        return listExam;
    }
}
