package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DatabaseChild extends DatabaseHelper {
    public DatabaseChild(Context context) {
        super(context);
    }

    public boolean insertData(int parentID, String userName, String password, String name){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_parent_ID, parentID);
        contentValues.put(Col_username, userName);
        contentValues.put(Col_password, password);
        contentValues.put(Col_name, name);
        contentValues.put(Col_points, 0);
        long result = db.insert(Table_Child, null, contentValues);
        return (result == -1 )? false: true;
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
