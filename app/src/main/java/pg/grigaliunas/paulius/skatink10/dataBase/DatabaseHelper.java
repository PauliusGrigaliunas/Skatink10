package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public abstract class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Skatink.db";
    public static final String Table_User = "user";
    public static final String Table_Parent = "parent";
    public static final String Table_Child = "child";
    public static final String Table_Tasks = "tasks";
    public static final String Table_Email = "email";
    public static final String Table_Assignment = "assignment";
    public static final String Col_ID = "ID";
    public static final String Col_Nr = "Nr";
    public static final String Col_username = "username";
    public static final String Col_password = "password";
    public static final String Col_name = "name";
    public static final String Col_surname = "surname";
    public static final String Col_email = "email";
    public static final String Col_phone = "phone";
    public static final String Col_user_ID = "user_ID";
    public static final String Col_parent_ID = "parent_ID";
    public static final String Col_child_ID = "child_ID";
    public static final String Col_task_NR = "task_NR";
    public static final String Col_points = "points";
    public static final String Col_date = "date ";
    public static final String Col_text = "text ";
    public static final String Col_confirmed = "confirmed";
    protected SQLiteDatabase db = this.getWritableDatabase();


    protected final String CreateUserTable =
            "Create Table " + Table_User + " (" +
                    Col_ID + " INTEGER PRIMARY KEY, " +
                    Col_username + " text UNIQUE, " +
                    Col_password + " text, " +
                    Col_name + " text, " +
                    Col_confirmed + " BOOLEAN )";

    protected final String CreateParentTable =
            "Create Table " + Table_Parent + " (" +
                    Col_ID + " INTEGER PRIMARY KEY, " +
                    Col_user_ID + " INTEGER, " +
                    Col_surname + " text, " +
                    Col_email + " text UNIQUE, " +
                    Col_phone + " text, " +
                    "FOREIGN KEY("+ Col_user_ID +") REFERENCES " + Table_User + "("+Col_ID+"))";

    protected  final String CreateChildTable =
            "Create Table " + Table_Child + " (" +
                    Col_ID + " INTEGER PRIMARY KEY, "+
                    Col_parent_ID + " INTEGER, " +
                    Col_user_ID + " INTEGER, " +
                    Col_points + " INTEGER, " +
                    "FOREIGN KEY("+Col_parent_ID+") REFERENCES " + Table_Parent + "("+Col_ID+"), " +
                    "FOREIGN KEY("+ Col_user_ID +") REFERENCES " + Table_User + "("+Col_ID+"))";

    protected  final String CreateTaskTable =
            "Create Table " + Table_Tasks+ " (" +
                    Col_Nr + " INTEGER PRIMARY KEY, "+
                    Col_name + " text, " +
                    Col_points + " INTEGER) ";

    protected  final String CreateAssigmentTable =
            "Create Table " + Table_Assignment+ " (" +
                    Col_Nr + " INTEGER PRIMARY KEY, "+
                    Col_child_ID + " INTEGER, " +
                    Col_name + " text, " +
                    Col_points + " INTEGER, " +
                    Col_date + " text, " +
                    Col_confirmed + " BOOLEAN, " +
                    "FOREIGN KEY("+Col_child_ID+") REFERENCES " + Table_User  + "("+Col_ID+"))";


    protected  final String CreateEmailTable =
            "Create Table " + Table_Email+ " (" +
                    Col_Nr + " INTEGER PRIMARY KEY, "+
                    Col_parent_ID + " INTEGER, " +
                    Col_child_ID + " INTEGER, " +
                    Col_text + " text, " +
                    Col_points + " INTEGER, " +
                    Col_date + " text, " +
                    "FOREIGN KEY("+Col_parent_ID+") REFERENCES " + Table_User  + "("+Col_ID+")," +
                    "FOREIGN KEY("+Col_child_ID+") REFERENCES " + Table_User  + "("+Col_ID+"))";

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateUserTable);
        db.execSQL(CreateParentTable);
        db.execSQL(CreateChildTable);
        db.execSQL(CreateTaskTable);
        db.execSQL(CreateAssigmentTable);
        db.execSQL(CreateEmailTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table if Exists " + Table_Email );
        db.execSQL("Drop Table if Exists " + Table_Assignment );
        db.execSQL("Drop Table if Exists " + Table_Tasks );
        db.execSQL("Drop Table if Exists " + Table_Parent );
        db.execSQL("Drop Table if Exists " + Table_Child );
        db.execSQL("Drop Table if Exists " + Table_User );
        onCreate(db);
    }
    public boolean insertParentData(String userName, String password, String name, String surname, String email, String phone){
        if( insertUserData(userName,password,name,true)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_user_ID, findByUserName(userName, password).getInt(0));
            contentValues.put(Col_surname, surname);
            contentValues.put(Col_email, email);
            contentValues.put(Col_phone, phone);
            return (db.insert(Table_Parent, null, contentValues) == -1 )? false: true;
        }
        else return false;
    }
    public boolean insertChildData(int parentID, String userName, String password, String name){
        if( insertUserData(userName,password,name,false)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Col_parent_ID, parentID);
            contentValues.put(Col_user_ID, findByUserName(userName, password).getInt(0));
            contentValues.put(Col_points, 0);
            return (db.insert(Table_Child, null, contentValues) == -1 )? false: true;
        }
        else return false;
    }

    public boolean insertTaskData(String name, int points ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_name, name);
        contentValues.put(Col_points, points);
        long result = db.insert(Table_Tasks, null, contentValues);
        return (result == -1 )? false: true;
    }

    public boolean insertAssignmentData(int childID, String name, int taskNR, boolean confirmed ){

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_child_ID, childID);
        contentValues.put(Col_name, name);
        contentValues.put(Col_points, taskNR);
        contentValues.put(Col_date, strDate);
        contentValues.put(Col_confirmed, confirmed);
        long result = db.insert(Table_Assignment, null, contentValues);
        return (result == -1 )? false: true;
    }

    public boolean insertEmailData(int senderID, int receiverID, String text, int points){

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_child_ID, senderID);
        contentValues.put(Col_parent_ID, receiverID);
        contentValues.put(Col_text, text);
        contentValues.put(Col_points, points);
        contentValues.put(Col_date, strDate);
        long result = db.insert(Table_Email, null, contentValues);
        return (result == -1 )? false: true;
    }
    public boolean insertUserData(String userName, String password, String name, boolean confirmed){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_username, userName);
        contentValues.put(Col_password, password);
        contentValues.put(Col_name, name);
        contentValues.put(Col_confirmed, confirmed);
        long result = db.insert(Table_User, null, contentValues);
        return (result == -1 )? false: true;
    }


    public abstract Cursor showData();
    public abstract Cursor findDataById(int id);
    public abstract boolean delete(int id);
    public Cursor findByParentId(int id) { return null;}


    public Cursor validateByUserName(String username, String password){
        return findByUserName(username, password);
    }

    public Cursor findByUserName(String username, String password) {
        Cursor c = db.rawQuery("SELECT * FROM " + Table_User +
                " WHERE " + Col_username + " ='" + username.trim() +
                "' AND " + Col_password + " ='" + password.trim() + "'", null);
        if (c.moveToFirst()) return c;
        else return null;

    }
    public boolean addPoints (int id, int points){return false;}
}

