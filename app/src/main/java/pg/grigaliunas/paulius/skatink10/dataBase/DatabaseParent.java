package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DatabaseParent extends DatabaseHelper {
    public DatabaseParent(Context context) {
        super(context);
    }

    public boolean insertData(String userName, String password, String name, String surname, String email, String phone){
        if( super.insertUserData(userName,password,name,true)) {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put(Col_user_ID, validateByUserName(userName, password).getInt(0));
            contentValues2.put(Col_surname, surname);
            contentValues2.put(Col_email, email);
            contentValues2.put(Col_phone, phone);
            return (db.insert(Table_Parent, null, contentValues2) == -1 )? false: true;
        }
       else return false;
    }

    @Override
    public Cursor showData() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Parent , null);
        return cursor;
    }

    @Override
    public Cursor findDataById(int id) {

        Cursor c = db.rawQuery("SELECT * FROM " + Table_User +
                " WHERE " +Col_ID+" = "+ id  , null);
        if (c.moveToFirst()) return c;
        else return null;
    }

    @Override
    public boolean delete(int id) {
        int result = db.delete(Table_Parent, Col_user_ID + "=" + id, null);
        result += db.delete(Table_User, Col_ID + "=" + id, null);
        return (result == 0) ? false : true;
    }

    @Override
    public Cursor validateByUserName(String username, String password){
        Cursor c = db.rawQuery("SELECT * FROM " + Table_User +" a" +
                " INNER JOIN " + Table_Parent  +" on a." + Col_ID+  " = " + Col_user_ID +
                " WHERE " +Col_username+ " ='"+username.trim()+
                "' AND " +Col_password+ " ='"+password.trim()+"'" , null);
        if (c.moveToFirst()) return c;
        else return null;
    }
}
