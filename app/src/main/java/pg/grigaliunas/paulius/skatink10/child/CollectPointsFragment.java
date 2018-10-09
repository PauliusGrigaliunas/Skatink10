package pg.grigaliunas.paulius.skatink10.child;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.SignupActivity;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseTask;
import pg.grigaliunas.paulius.skatink10.parent.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectPointsFragment extends Fragment {

    private DatabaseHelper mydb;
    private Spinner spinner;
    private Button button;
    private HashMap<String, String> items;

    public CollectPointsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_collect_points, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        button = (Button) view.findViewById(R.id.button);

        fillSpinner();

        return view;
    }

    private void fillSpinner(){

        mydb = new DatabaseTask(getActivity());
        Cursor c = mydb.showData();

        ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
        ArrayList<String> arrayOfName = new ArrayList<>();
        ArrayList<String> arrayOfPoint = new ArrayList<>();

        while (c.moveToNext()){
            Toast.makeText(getActivity(), c.getString(1), Toast.LENGTH_LONG).show();
            items = new HashMap<String, String>();
            items.put("id", c.getString(0));
            items.put("name", c.getString(1));
            items.put("points",c.getString(2));
            arrayList.add(items);
            arrayOfName.add(c.getString(1) + "\n" +c.getString(2));

        }


        String from[]={"id","name","points"};
        int to[] = {R.id.idText, R.id.nameText, R.id.pointText};

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                arrayList,
                R.layout.list_row,
                from, to );


        adapter.setDropDownViewResource(R.layout.list_row);
        spinner.setAdapter(adapter);


       /*ArrayAdapter<String>adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arrayOfName);


        spinner.setAdapter(adapter);*/
       // spinner.setOnItemSelectedListener(this);

    }
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }
}
