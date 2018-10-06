package pg.grigaliunas.paulius.skatink10;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildFragment extends Fragment {

    private DatabaseHelper mydb;
    private UserData userData = UserData.getInstance();
    private ListView listView;
    private FloatingActionButton fab;

    private ArrayList arrayList;
    private HashMap<String, String> hmap;

    private TextView textView;
    public ChildFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);

        mydb = new DatabaseHelper(getActivity());
        listView = (ListView) view.findViewById(R.id.listView);
        textView = (TextView) view.findViewById(R.id.textView4);

        showList();

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        openChildActivity();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        showList();
    }

    private void openChildActivity() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChildActivity.class));
            }
        });
    }


    private void showList(){

        arrayList = new ArrayList<HashMap<String,String>>();
        try{

            Cursor c = mydb.findChildren(userData.getData().getInt(0));


            while(c.moveToNext())
            {
                hmap= new HashMap<String, String>();
                hmap.put("id", c.getString(0));
                hmap.put("name", c.getString(4));
                hmap.put("points",c.getString(5));
                arrayList.add(hmap);
            }
        }
        catch(Exception e){
            Log.e("error",e.getMessage());

        }

        String from[]={"id","name","points"};
        int to[] = {R.id.idText, R.id.nameText, R.id.pointText};

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                arrayList,
                R.layout.list_row,
                from, to );

        listView.setAdapter(adapter);
    }
}
