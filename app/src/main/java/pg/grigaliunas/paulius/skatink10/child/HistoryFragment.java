package pg.grigaliunas.paulius.skatink10.child;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.UserData;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseAssigment;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    private ListView listView;
    private ArrayList arrayList;
    private HashMap<String, String> hmap;

    private DatabaseHelper mydb ;
    private UserData userData = UserData.getInstance();
    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);
        listView = view.findViewById(R.id.listView);
        mydb = new DatabaseAssigment(getActivity());
        showList();
        return view;

    }
    private void showList(){

        arrayList = new ArrayList<HashMap<String,String>>();
        try{

            Cursor c = mydb.findDataById(userData.getData().getInt(0));


            while(c.moveToNext())
            {
                hmap= new HashMap<String, String>();
                hmap.put("id", c.getString(0));
                hmap.put("name", c.getString(3));
                hmap.put("time", c.getString(3));
                hmap.put("points",c.getString(4));
                arrayList.add(hmap);
            }
        }
        catch(Exception e){
            Log.e("error",e.getMessage());

        }

        String from[]={"name","time","points"};
        int to[] = {R.id.idText, R.id.taskText, R.id.pointText};

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                arrayList,
                R.layout.list_row,
                from, to );

        listView.setAdapter(adapter);
    }
}
