package pg.grigaliunas.paulius.skatink10.parent;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.UserData;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseEmail;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private DatabaseHelper mydb;
    private ListView listView;
    private ArrayList arrayList;
    private HashMap<String, String> hmap;
    private UserData userData = UserData.getInstance();
    private Cursor cursor;
    private int childId;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_notification, container, false);
        mydb = new DatabaseEmail(getActivity());
        listView = (ListView) view.findViewById(R.id.listView);
        showList();
        return view;
    }
    private void showList(){

        arrayList = new ArrayList<HashMap<String,String>>();
        try{

            cursor = mydb.findByParentId(userData.getData().getInt(0));


            while(cursor.moveToNext())
            {
                hmap= new HashMap<String, String>();
                hmap.put("nr", cursor.getString(0));
                hmap.put("name", cursor.getString(9));
                hmap.put("task", cursor.getString(3));
                hmap.put("points", cursor.getString(4));
                arrayList.add(hmap);
                childId = cursor.getInt(6);
            }
        }
        catch(Exception e){
            Log.e("error",e.getMessage());

        }

        String from[]={"nr","name", "task","points"};
        int to[] = {R.id.idText, R.id.nameText, R.id.taskText, R.id.pointText};

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                arrayList,
                R.layout.list_row2,
                from, to );

        listView.setAdapter(adapter);
        selectItemFromList();
    }

    private void selectItemFromList(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap element = (HashMap) arrayList.get(position);


                Bundle bundle = new Bundle();
                bundle.putInt("id", childId);
                bundle.putInt("nr", Integer.parseInt(element.get("nr").toString()));
                bundle.putString("name", element.get("name").toString());
                bundle.putString("task",element.get("task").toString());
                bundle.putInt("points",Integer.parseInt(element.get("points").toString()));

                Fragment fragment = new NotificationInfoFragment();
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
