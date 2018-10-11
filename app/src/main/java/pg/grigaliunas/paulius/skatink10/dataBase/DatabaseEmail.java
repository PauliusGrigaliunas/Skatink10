package pg.grigaliunas.paulius.skatink10.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseEmail extends DatabaseHelper {
    public DatabaseEmail(Context context) {
        super(context);
    }

    public boolean insertData(int senderID, int receiverID, String text){

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_child_ID, senderID);
        contentValues.put(Col_parent_ID, receiverID);
        contentValues.put(Col_text, text);
        contentValues.put(Col_date, strDate);
        long result = db.insert(Table_Email, null, contentValues);
        return (result == -1 )? false: true;
    }

    @Override
    public Cursor showData() {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Email , null);
        return cursor;
    }

    @Override
    public Cursor findDataById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Email +
                " WHERE " + Col_Nr + " = " + id, null);
        return cursor;
    }

    @Override
    public boolean delete(int id) {
        int result = db.delete(Table_Email, Col_Nr + "=" + id, null);
        return (result == 0) ? false : true;
    }

    @Override
    public Cursor findByParentId(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_Email + " a" +
                " INNER JOIN " + Table_User + " b on a." + Col_child_ID + " = b." + Col_ID +
                " WHERE a." + Col_parent_ID + " = " + id, null);
        return cursor;
    }
}
