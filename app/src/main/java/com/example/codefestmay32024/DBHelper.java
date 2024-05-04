package com.example.codefestmay32024;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.codefestmay32024.models.AccountModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{

    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public DBHelper(@Nullable Context context){
        super(context, "accounts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Accounts(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "address VARCHAR," +
                "bdate VARCHAR," +
                "contact VARCHAR," +
                "username VARCHAR," +
                "password VARCHAR," +
                "pin VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Accounts");
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String name, String address, String bdate, String contact, String username, String password, String pin)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("bdate", bdate);
        values.put("contact", contact);
        values.put("username", username);
        values.put("password", password);
        values.put("pin", pin);

        long i = db.insert("Accounts", null, values);
        if(i != -1)
        {
            return true;
        }
        else{
            return false;
        }
    }

   /* public boolean updateData(int id, String name, String address, String bdate, String contact, String username, String password)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("bdate", bdate);
        values.put("contact", contact);
        values.put("username", username);
        values.put("password", password);


        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};

//        long i = db.update("Accounts", values, selection, selectionArgs);


        Cursor cursor = db.rawQuery("UPDATE Accounts SET name = '"+name+"', address = '"+address+"', bdate = '"+bdate+"', contact = '"+contact+"', username = '"+username+"', password = '"+password+"' WHERE id = '"+id+"'", null);


        if(cursor.getCount() > 0) {

            return true;
        }
        else
        {
            return false;
        }
    }*/


    public boolean updateData(int id, String name, String address, String bdate, String contact,
                              String username, String password) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("address", address);
        values.put("bdate", bdate);
        values.put("contact", contact);
        values.put("username", username);
        values.put("password", password);

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(id)};


        int rowsAffected = db.update("Accounts", values, selection, selectionArgs);

        if (rowsAffected > 0) {
            return true;
        } else {
            return false;
        }
    }



    public boolean deleteData(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();


       long i =  db.delete("Accounts", "id = ?", new String[]{String.valueOf(id)} );

        if(i > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public Cursor findAccount(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Accounts WHERE username = '"+username+"' AND password = '"+password+"'", null);
        return cursor;
    }

    public List<AccountModel> getAllList()
    {
        List<AccountModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Accounts", null);
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String bdate = cursor.getString(3);
            String contact = cursor.getString(4);
            String user = cursor.getString(5);
            String pass = cursor.getString(6);
            String pin = cursor.getString(7);

            list.add(new AccountModel(id, name, address, bdate, contact, user, pass, pin));
        }


        return list;
    }


    public Cursor findAccountByPin(String pin)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM Accounts WHERE pin = '"+pin+"'", null);
    }

}
