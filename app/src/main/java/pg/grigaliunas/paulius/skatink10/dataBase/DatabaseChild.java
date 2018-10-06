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
    public Cursor findData(int id){
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Child +
                " WHERE " +Col_task_NR+" = "+ id , null);
        return cursor;
    }


}
