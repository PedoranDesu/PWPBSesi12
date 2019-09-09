package com.pedoran.pwpbsesi12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "UserInfo";
    private static final String TABLE_NAME = "tbl_user";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createUserTable = "Create Table "+TABLE_NAME+"("+KEY_NAME+" TEXT PRIMARY KEY,"+KEY_AGE+" INTEGER)";
        sqLiteDatabase.execSQL(createUserTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "Drop Table If Exists "+TABLE_NAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert(Person person){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_NAME,person.getName());
        val.put(KEY_AGE,person.getAge());

        db.insert(TABLE_NAME,null,val);
    }

    public List<Person> selectUserData(){
        ArrayList<Person> userList = new ArrayList<Person>();

        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {KEY_NAME,KEY_AGE};

        Cursor c = db.query(TABLE_NAME,columns,null,null,null,null,null);

        while(c.moveToNext()){
            String name = c.getString(0);
            int age = c.getInt(1);

            Person jalmi = new Person();
            jalmi.setName(name);
            jalmi.setAge(age);
            userList.add(jalmi);
        }

        return userList;
    }

    public void delete(String name){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = KEY_NAME+"='"+name+"'";
        db.delete(TABLE_NAME,whereClause,null);
    }

    public void update(Person person){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_AGE,person.getAge());
        String whereClause = KEY_NAME+"='"+person.getName()+"'";
        db.update(TABLE_NAME,val,whereClause,null);

    }
}
