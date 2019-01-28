package com.enamelbd.vatchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.text.format.Time;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "DreamersNasa1.db";
   public static final String CONTACTS_TABLE_NAME = "enamelnasa";
   
  
   public static final int KEY_ID = 0;


   private HashMap hp;

   public DBHelper(Context context)
   {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
	  
      db.execSQL(
      "CREATE TABLE enamelnasa" +
      "(id INTEGER PRIMARY KEY,name TEXT,ra_deg DOUBLE,dec_deg DOUBLE,today TEXT,icon TEXT,icon_orbit TEXT,alter_name TEXT,type TEXT,yesterday TEXT,tenday TEXT,mean TEXT,peak TEXT,days TEXT,last_day TEXT,time TEXT,bookmark TEXT,url TEXT)"
      );
   }

   
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS enamelnasa");
      onCreate(db);
   }

   public boolean insertContact  (String name, double ra_deg, double dec_deg, String today,String icon, String icon_orbit, String alter_name, String type, String yesterday, String tenday, String mean, String peak, String days, String last_day, String time, String bookmark, String url)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();

      
      contentValues.put("name", name);
      contentValues.put("ra_deg", ra_deg);
      contentValues.put("dec_deg", dec_deg);
      
      contentValues.put("today", today);
      contentValues.put("icon", icon);
      contentValues.put("icon_orbit", icon_orbit);
      contentValues.put("alter_name", alter_name);
      contentValues.put("type", type);
      contentValues.put("yesterday", yesterday);
      contentValues.put("tenday", tenday);
      contentValues.put("mean", mean);
      contentValues.put("peak", peak);
      contentValues.put("days", days);
      contentValues.put("last_day", last_day);
      contentValues.put("time", time);
      contentValues.put("bookmark", bookmark);
      contentValues.put("url", url);

      db.insert("enamelnasa", null, contentValues);
      return true;
   }
   public Cursor getData(String name, String url){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from enamelnasa where name='"+name+"' AND url='"+url+"'", null );
      return res;
   }
   
   

   
   
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
      return numRows;
   }
   

   

   
   public boolean updateContact (String name, double ra_deg, double dec_deg, String today, String icon, String icon_orbit, String alter_name, String type, String yesterday, String tenday, String mean, String peak, String days, String last_day, String time, String bookmark, String url)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();


     // contentValues.put("company", company);
     // contentValues.put("name", name);
      contentValues.put("ra_deg", ra_deg);
      contentValues.put("dec_deg", dec_deg);
      
      contentValues.put("today", today);
      contentValues.put("icon", icon);
      contentValues.put("icon_orbit", icon_orbit);
      contentValues.put("alter_name", alter_name);
      contentValues.put("type", type);
      contentValues.put("yesterday", yesterday);
      contentValues.put("tenday", tenday);
      contentValues.put("mean", mean);
      contentValues.put("peak", peak);
      contentValues.put("days", days);
      contentValues.put("last_day", last_day);
      contentValues.put("time", time);
     // contentValues.put("bookmark", bookmark);
      contentValues.put("url", url);
      
      
      db.update("enamelnasa", contentValues, "name = ? ", new String[] { name } );
      return true;
   }
   
   
   
   
   public boolean addBookmark (String id, String bookmark)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();


     // contentValues.put("company", company);
     // contentValues.put("name", name);
      contentValues.put("bookmark", bookmark);
     // contentValues.put("url", url);
     
      
      db.update("enamelnasa", contentValues, "id = ? ", new String[] { id } );
        return true;
   }
   
   
   

   public Integer deleteContact (Integer id)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("enamelnasa", 
      "id = ? ", 
      new String[] { Integer.toString(id) });
   }

   
   
   public Cursor getAllCompany(String url)
   {//hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from enamelnasa where url='"+url+"' order by id asc", null );
      
   return res;
   }
   
   
   
   public Cursor getAllBookmark()
   {//hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from enamelnasa where bookmark='true' order by id asc", null );
      
   return res;
   }
   
   
   
   public Cursor getAllCompanyByRa(String url)
   {//hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from enamelnasa where url='"+url+"' order by ra_deg asc", null );
      
   return res;
   }
   
 
   
   public Cursor getAllCompanyByDec(String url)
   {//hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from enamelnasa where url='"+url+"' order by dec_deg asc", null );
      
   return res;
   }
   
   
  
  
   
 
}