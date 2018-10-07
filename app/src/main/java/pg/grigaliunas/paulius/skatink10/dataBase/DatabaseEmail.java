package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.Calendar;

public class DatabaseEmail extends DatabaseHelper {
    public DatabaseEmail(Context context) {
        super(context);
    }

    public boolean insertData(int senderID, int receiverID, String text){

        Calendar cal = Calendar.getInstance();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_child_ID, senderID);
        contentValues.put(Col_parent_ID, receiverID);
        contentValues.put(Col_text, text);
        contentValues.put(Col_date, cal.getTime().toString());
        long result = db.insert(Table_Email, null, contentValues);
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
