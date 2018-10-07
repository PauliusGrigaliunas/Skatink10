package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.Calendar;

public class DatabaseAssigment extends DatabaseHelper {
    public DatabaseAssigment(Context context) {
        super(context);
    }

    public boolean insertData(int childID, int taskNR, boolean confirmed ){

        Calendar cal = Calendar.getInstance();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_child_ID, childID);
        contentValues.put(Col_task_NR, taskNR);
        contentValues.put(Col_date, cal.getTime().toString());
        contentValues.put(Col_confirmed, confirmed);
        long result = db.insert(Table_Assigment, null, contentValues);
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


}
