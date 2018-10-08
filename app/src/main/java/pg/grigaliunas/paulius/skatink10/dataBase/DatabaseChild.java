package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DatabaseChild extends DatabaseHelper {

    public DatabaseChild(Context context) {
        super(context);
    }

    public boolean insertData(int parentID, String userName, String password, String name){

        if( super.insertUserData(userName,password,name,true)) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(Col_user_ID, validateByUserName(userName, password).getInt(0));
            contentValues2.put(Col_parent_ID, parentID);
            contentValues2.put(Col_points, 0);
            return (db.insert(Table_Child, null, contentValues2) == -1 )? false: true;
        }
        else return false;
    }

    @Override
    public Cursor validateByUserName(String username, String password){
        Cursor c = db.rawQuery("SELECT * FROM " + Table_User +" a" +
                " INNER JOIN " + Table_Child  +" on a." + Col_ID+  " = " + Col_user_ID +
                " WHERE " +Col_username+ " ='"+username.trim()+
                "' AND " +Col_password+ " ='"+password.trim()+"'" , null);
        if (c.moveToFirst()) return c;
        else return null;
    }

    @Override
    public Cursor showData(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Child , null);
        return cursor;
    }

    @Override
    public Cursor findDataById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_User + " a" +
                " INNER JOIN " + Table_Child + " on a." + Col_ID + " = " + Col_user_ID +
                " WHERE a." + Col_ID + " = " + id, null);
                cursor.moveToFirst();
        return cursor;
    }

    @Override
    public boolean delete(int id) {
        int result = db.delete(Table_Child, Col_user_ID + "=" + id, null);
        result += db.delete(Table_User, Col_ID + "=" + id, null);
        return (result == 0) ? false : true;
    }

    @Override
    public Cursor findByParentId(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_User + " a" +
                " INNER JOIN " + Table_Child + " on a." + Col_ID + " = " + Col_user_ID +
                " WHERE " + Col_parent_ID + " = " + id, null);
        cursor.moveToFirst();
        return cursor;

    }
    public boolean addPoints (int id, int points) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Child +
                " WHERE " + Col_user_ID + " = " + id, null);
        cursor.moveToFirst();

        int sum = cursor.getInt(3) + points;

        ContentValues newValues = new ContentValues();
        newValues.put( Col_points, sum);

        int result = db.update(Table_Child, newValues, Col_user_ID +" = " + cursor.getInt(2), null);
        return (result == -1 )? false: true;

    }
}
