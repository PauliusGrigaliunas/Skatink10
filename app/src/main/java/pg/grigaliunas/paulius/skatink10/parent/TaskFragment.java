package pg.grigaliunas.paulius.skatink10.parent;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseTask;
import pg.grigaliunas.paulius.skatink10.UserData;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskFragment extends Fragment {

    private DatabaseHelper mydb;
    private UserData userData = UserData.getInstance();
    private ListView listView;
    private ArrayList arrayList;
    private HashMap<String, String> hmap;

    FloatingActionButton fab;
    public TaskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        openTaskActivity();

        mydb = new DatabaseTask(getActivity());
        listView = (ListView) view.findViewById(R.id.listView);
        showList();


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        showList();
    }

    private void openTaskActivity() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TaskActivity.class));
            }
        });
    }
    private void showList(){

        arrayList = new ArrayList<HashMap<String,String>>();
        try{

            Cursor c = mydb.showData();


            while(c.moveToNext())
            {
                hmap= new HashMap<String, String>();
                hmap.put("id", c.getString(0));
                hmap.put("name", c.getString(1));
                hmap.put("points",c.getString(2));
                arrayList.add(hmap);
            }
        }
        catch(Exception e){
            Log.e("error",e.getMessage());

        }

        String from[]={"id","name","points"};
        int to[] = {R.id.idText, R.id.taskText, R.id.pointText};

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                arrayList,
                R.layout.list_row,
                from, to );

        listView.setAdapter(adapter);

        selectItemFromList();
    }
    private void selectItemFromList() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap element = (HashMap) arrayList.get(position);

                int name = Integer.parseInt(element.get("id").toString());

                Bundle bundle = new Bundle();
                bundle.putInt("data", name);

                Fragment fragment = new TaskInfoFragment();
                fragment.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }
}
