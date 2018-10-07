package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class DatabaseTask extends DatabaseHelper {
    public DatabaseTask(Context context) {
        super(context);
    }

    public boolean insertData(String name, int points ){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_name, name);
        contentValues.put(Col_points, points);
        long result = db.insert(Table_Tasks, null, contentValues);
        return (result == -1 )? false: true;
    }
    @Override
    public Cursor showData(){
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Tasks , null);
        return cursor;
    }

    @Override
    public Cursor findDataById(int id) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
