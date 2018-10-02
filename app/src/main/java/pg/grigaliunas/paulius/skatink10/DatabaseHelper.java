package pg.grigaliunas.paulius.skatink10;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Skatink.db";
    public static final String Table_Parent = "parent";
    public static final String Table_Child = "child";
    public static final String Table_Tasks = "tasks";
    public static final String Table_Assigment = "assigment";
    public static final String Col_ID = "ID";
    public static final String Col_Nr = "Nr";
    public static final String Col_username = "username";
    public static final String Col_password = "password";
    public static final String Col_name = "name";
    public static final String Col_surname = "surname";
    public static final String Col_email = "email";
    public static final String Col_phone = "phone";
    public static final String Col_parent_ID = "parent_ID";
    public static final String Col_child_ID = "child_ID";
    public static final String Col_task_NR = "task_NR";
    public static final String Col_points = "points";
    public static final String Col_date = "date ";
    public static final String Col_confirmed = "confirmed";
    private SQLiteDatabase db = this.getWritableDatabase();

    private final String CreateParentTable =
            "Create Table " + Table_Parent + " (" +
                    Col_ID + " INTEGER PRIMARY KEY, " +
                    Col_username + " text UNIQUE, " +
                    Col_password + " text, " +
                    Col_name + " text, " +
                    Col_surname + " text , " +
                    Col_email + " text UNIQUE, " +
                    Col_phone + " text )";

    private final String CreateChildTable =
            "Create Table " + Table_Child + " (" +
                    Col_ID + " INTEGER PRIMARY KEY, "+
                    Col_parent_ID + " INTEGER, " +
                    Col_username + " text UNIQUE, " +
                    Col_password + " text, " +
                    Col_name + " text, " +
                    Col_points + " INTEGER, " +
                    "FOREIGN KEY("+Col_parent_ID+") REFERENCES " + Table_Parent + "("+Col_ID+"))";


    private final String CreateTaskTable =
            "Create Table " + Table_Tasks+ " (" +
                    Col_Nr + " INTEGER PRIMARY KEY, "+
                    Col_name + " text, " +
                    Col_points + " INTEGER) ";

    private final String CreateAssigmentTable =
            "Create Table " + Table_Assigment+ " (" +
                    Col_Nr + " INTEGER PRIMARY KEY, "+
                    Col_child_ID + " INTEGER, " +
                    Col_task_NR + " INTEGER, " +
                    Col_date + " text, " +
                    Col_confirmed + " BOOLEAN, " +
                    "FOREIGN KEY("+Col_child_ID+") REFERENCES " + Table_Child  + "("+Col_ID+")," +
                    "FOREIGN KEY("+Col_child_ID+") REFERENCES " + Table_Tasks  + "("+Col_Nr+"))";


    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateParentTable);
        db.execSQL(CreateChildTable);
        db.execSQL(CreateTaskTable);
        db.execSQL(CreateAssigmentTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if Exists " + Table_Parent );
        db.execSQL("Drop Table if Exists " + Table_Child );
        db.execSQL("Drop Table if Exists " + Table_Tasks );
        onCreate(db);
    }
    public boolean insertParentData(String userName, String password, String name, String surname, String email, String phone){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_username, userName);
        contentValues.put(Col_password, password);
        contentValues.put(Col_name, name);
        contentValues.put(Col_surname, surname );
        contentValues.put(Col_email, email);
        contentValues.put(Col_phone, phone );
        long result = db.insert(Table_Parent, null, contentValues);
        return (result == -1 )? false: true;
    }
    public boolean insertChildData(int parentID, String userName, String password, String name){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_parent_ID, parentID);
        contentValues.put(Col_username, userName);
        contentValues.put(Col_password, password);
        contentValues.put(Col_name, name);
        contentValues.put(Col_points, 0);
        long result = db.insert(Table_Child, null, contentValues);
        return (result == -1 )? false: true;
    }

    public boolean insertTaskData(String name, int points ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_name, name);
        contentValues.put(Col_points, points);
        long result = db.insert(Table_Tasks, null, contentValues);
        return (result == -1 )? false: true;
    }

    public boolean insertAssigmentData(int childID, int taskNR, boolean confirmed ){

        Calendar cal = Calendar.getInstance();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_child_ID, childID);
        contentValues.put(Col_task_NR, taskNR);
        contentValues.put(Col_date, cal.getTime().toString());
        contentValues.put(Col_confirmed, confirmed);
        long result = db.insert(Table_Assigment, null, contentValues);
        return (result == -1 )? false: true;
    }

    public Cursor ValidateByUserName(String username, String password){
        Cursor c = db.rawQuery("SELECT * FROM " + Table_Parent +
                " WHERE " +Col_username+ " ='"+username.trim()+
                "' AND " +Col_password+ " ='"+password.trim()+"'" , null);
        if (c.moveToFirst()) return c;
        else return null;
    }
    public Cursor findByID(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Parent +
                " WHERE " +Col_ID+" = "+id , null);
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor findChildren(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Child +
                " WHERE " +Col_parent_ID+" = "+id , null);
        return cursor;
    }


}

