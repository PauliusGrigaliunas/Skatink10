package pg.grigaliunas.paulius.skatink10;

import android.database.Cursor;

public class UserData {
    private static UserData instance;

    // Global variable
    private Cursor cursor;

    // Restrict the constructor from being instantiated
    private UserData(){}

    public void setData(Cursor cursor){
        this.cursor = cursor;
    }
    public Cursor getData(){
        return this.cursor;
    }

    public static synchronized UserData getInstance(){
        if(instance == null){
            instance = new UserData();
        }
        return instance;
    }
    public void clear()
    {
        instance = null;
    }
}
