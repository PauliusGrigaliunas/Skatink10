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
            contentValues2.put(Col_user_ID, ValidateByUserName(userName, password).getInt(0));
            contentValues2.put(Col_parent_ID, parentID);
            contentValues2.put(Col_points, 0);
            return (db.insert(Table_Child, null, contentValues2) == -1 )? false: true;
        }
        else return false;
    }

    @Override
    public Cursor ValidateByUserName(String username, String password){
        Cursor c = db.rawQuery("SELECT * FROM " + Table_User +
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
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Child +
                " WHERE " +Col_ID+" = "+ id , null);
        return cursor;
    }

    @Override
    public boolean delete(int id) {
        int result = db.delete(Table_Child, Col_ID + "=" + id, null);
        return (result == 0 )? false: true;
    }

    @Override
    public Cursor findByParentId(int id){
    Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Child +
            " WHERE " +Col_parent_ID+" = "+id , null);
        return cursor;
    }
}
