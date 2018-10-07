package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DatabaseParent extends DatabaseHelper {
    public DatabaseParent(Context context) {
        super(context);
    }


    public boolean insertData(String userName, String password, String name, String surname, String email, String phone){

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

    @Override
    public Cursor showData() {
        return null;
    }

    @Override
    public Cursor findDataById(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Cursor ValidateByUserName(String username, String password){
        Cursor c = db.rawQuery("SELECT * FROM " + Table_Parent +
                " WHERE " +Col_username+ " ='"+username.trim()+
                "' AND " +Col_password+ " ='"+password.trim()+"'" , null);
        if (c.moveToFirst()) return c;
        else return null;
    }
}
