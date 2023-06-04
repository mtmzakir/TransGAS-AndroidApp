package com.ousl.transgas;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ousl.transgas.model.UserModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "login.db";
    public static final String TABLE_USERS = "users";
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ID = "UserID";
    public static final String COLUMN_USER_NAME = "UserName";
    public static final String COLUMN_EMAIL = "Email";
    public static final String COLUMN_ADDRESS = "Address";
    public static final String COLUMN_MOBILE_NUMBER = "MobileNumber";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_ORDER_ID = "orderID";
    public static final String COLUMN_ORDER_DATE = "orderDate";
    public static final String COLUMN_ORDER_TIME = "orderTime";
    public static final String COLUMN_ORDER_AMOUNT = "orderAmount";
    public static final String COLUMN_ORDER_ADDRESS = "orderAddress";
    public static final String COLUMN_ORDER_METHOD = "orderMethod";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        //Create Users Table
        String CREATE_USERS_TABLE_QUERY = "CREATE TABLE " + TABLE_USERS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_MOBILE_NUMBER + " TEXT, "
                + COLUMN_PASSWORD + " TEXT)";

//Create Orders Table
        String CREATE_ORDERS_TABLE_QUERY = "CREATE TABLE " + TABLE_ORDERS + " ("
                + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID + " INTEGER, "
                + COLUMN_ORDER_DATE + " TEXT, "
                + COLUMN_ORDER_TIME + " TEXT, "
                + COLUMN_ORDER_AMOUNT + " TEXT, "
                + COLUMN_ORDER_ADDRESS + " TEXT, "
                + COLUMN_ORDER_METHOD + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_ORDER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "))";

        MyDB.execSQL(CREATE_USERS_TABLE_QUERY);
        MyDB.execSQL(CREATE_ORDERS_TABLE_QUERY);


    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists orders");

    }

    //Add New User to DataBase
    public Boolean createNewUser(String username, String email,String address, String phoneNumber, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_NAME,username);
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_ADDRESS,address);
        contentValues.put(COLUMN_MOBILE_NUMBER,phoneNumber);
        contentValues.put(COLUMN_PASSWORD,password);
        long result = MyDB.insert(TABLE_USERS, null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    //Check User in DataBase
    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=?",new String[] {email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //Check User & Password in DataBase
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE email=? and password=?",new String[] {email,password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //Update User Details to DataBase
    public boolean updateProfileDetails(String username, String email,String address, String phoneNumber){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL,email);
        contentValues.put(COLUMN_ADDRESS,address);
        contentValues.put(COLUMN_MOBILE_NUMBER,phoneNumber);

        long count = MyDB.update(TABLE_USERS, contentValues, "username = ? ", new String[] {username});

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    //Update User Password to DataBase
    public boolean updatePassword(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD,password);

        long count = MyDB.update(TABLE_USERS, contentValues, "email = ? ", new String[] {email});

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }

    //Check User Old Password in DataBase
    public boolean checkOldPassword(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD,password);

        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            // The old password is correct
            return true;
        } else {
            // The old password is incorrect
            return false;
        }
    }

    //Add Address to DataBase
    public boolean addDeliveryDetails(String UserID, String orderAddress, String orderMethod){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID,UserID);
        contentValues.put(COLUMN_ORDER_ADDRESS,orderAddress);
        contentValues.put(COLUMN_ORDER_METHOD,orderMethod);
        long result = MyDB.insert(TABLE_ORDERS, null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }


    //Get and Set Current User Details
    public ArrayList<UserModel> getCurrentUserDetails(String usedEmail){
        ArrayList<UserModel> aList = new ArrayList<>();
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String query="SELECT * FROM " + TABLE_USERS + " WHERE email='"+usedEmail+"'";
        Cursor cursor=MyDB.rawQuery(query,null);

        if(cursor.moveToFirst()){
            String used_id = cursor.getString(0);
            String used_name = cursor.getString(1);
            String used_email = cursor.getString(2);
            String used_address = cursor.getString(3);
            String used_phone = cursor.getString(4);
            String used_password = cursor.getString(5);

            UserModel userModel=new UserModel();
            userModel.setUsed_id(used_id);
            userModel.setUsed_name(used_name);
            userModel.setUsed_email(used_email);
            userModel.setUsed_address(used_address);
            userModel.setUsed_phone(used_phone);
            userModel.setUsed_password(used_password);

            aList.add(userModel);

        }
        return aList;
    }
}

