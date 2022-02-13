package com.example.learn_english.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.learn_english.BuildConfig;
import com.example.learn_english.Object.Exam;
import com.example.learn_english.Object.Topic;
import com.example.learn_english.Object.Vocabulary;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Model extends SQLiteOpenHelper {
    String TAG = "Model";
    String DB_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";
    String DB_NAME = "db_english";

    FirebaseAuth mAuth = null;
    FirebaseFirestore db = null;

    Context context;
//    SQLiteDatabase db;

    public Model(@Nullable Context context) {
        super(context, "db_english", null, 1);
        this.context = context;

        if(mAuth == null)
            mAuth = FirebaseAuth.getInstance();

        if(db == null)
            db = FirebaseFirestore.getInstance();

//        if (isCopyDB()) {
//            db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
//        }
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

//        db = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public List<Topic> getListTopic(String lang) {
        List<Topic> listTopic = new ArrayList<>();
        CollectionReference colRef;

        colRef = db.collection("topics").document(mAuth.getCurrentUser().getUid()).collection(lang);

        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Log.d("MyApp", document.getId() + " " + document.get("name"));
                        listTopic.add(new Topic(document.getId().toString(), document.get("name").toString(), document.get("image").toString()));
                    }
                }
            }
        });

        Log.d("MyApp", listTopic.size() + "");
        return listTopic;
    }

    public CollectionReference getListTopic2(String lang) {
        List<Topic> listTopic = new ArrayList<>();
        CollectionReference colRef;

        colRef = db.collection("topics").document(mAuth.getCurrentUser().getUid()).collection(lang);

        return colRef;

//        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(QueryDocumentSnapshot document: task.getResult()){
//                        Log.d("MyApp", document.getId() + " " + document.get("name"));
//                        listTopic.add(new Topic(document.getId().toString(), document.get("name").toString(), document.get("image").toString()));
//                    }
//                }
//            }
//        });
//
//        Log.d("MyApp", listTopic.size() + "");
//        return listTopic;
    }

    public List<Vocabulary> getListVocabulary(int topicID) {
        String sql = "SELECT * FROM Itemchude WHERE Idchude = " + topicID;
//        Cursor cursor = db.rawQuery(sql, null);

        List<Vocabulary> listVocabulary = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            listVocabulary.add(new Vocabulary(cursor.getInt(0),
//                    cursor.getInt(1),
//                    cursor.getInt(6),
//                    cursor.getString(3),
//                    cursor.getString(4),
//                    cursor.getString(5),
//                    cursor.getString(7)));
//        }

        return listVocabulary;
    }

    public List<Vocabulary> getListVocabularyByKeyword(String keyword) {
        String sql = "SELECT * FROM Itemchude WHERE Tutienganh LIKE '%" + keyword + "%'" ;
//        Cursor cursor = db.rawQuery(sql, null);

        List<Vocabulary> listVocabulary = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            listVocabulary.add(new Vocabulary(cursor.getInt(0),
//                    cursor.getInt(1),
//                    cursor.getInt(6),
//                    cursor.getString(3),
//                    cursor.getString(4),
//                    cursor.getString(5),
//                    cursor.getString(7)));
//        }

        return listVocabulary;
    }

    public List<Exam> getListExam(int topicID, String language) {
        String sql = "SELECT * FROM test WHERE baiTest = " +  topicID  + " ORDER BY RANDOM() LIMIT 5";
//        Cursor cursor = db.rawQuery(sql, null);

        List<Exam> listExam = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            String ans1, ans2, ans3;
//            ans1 = cursor.getString(2);
//            ans2 = cursor.getString(3);
//            ans3 = cursor.getString(4);
//            if(language.equals("chinese")){
//                ans1 = cursor.getString(5);
//                ans2 = cursor.getString(6);
//                ans3 = cursor.getString(7);
//            }
//            listExam.add(new Exam(cursor.getInt(1),
//                    ans1,
//                    ans2,
//                    ans3,
//                    cursor.getString(8),
//                    "x"));
//        }

        return listExam;
    }
}
