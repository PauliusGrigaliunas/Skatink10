package pg.grigaliunas.paulius.skatink10.child;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.UserData;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseEmail;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectPointsFragment extends Fragment {

    private DatabaseHelper taskDb, emailDb;
    private Spinner spinner;
    private Button button;
    private HashMap<String, String> items;
    private int taskId;
    private String taskName;
    private int taskPoints;
    ArrayList<HashMap<String,String>> arrayList = new ArrayList<>();
    private UserData userData = UserData.getInstance();

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
        SentRequest();
        //Toast.makeText(getActivity(), taskId, Toast.LENGTH_LONG).show();

        return view;
    }

    private void fillSpinner(){

        emailDb =  new DatabaseEmail(getActivity());
        taskDb = new DatabaseTask(getActivity());
        Cursor c = taskDb.showData();




        while (c.moveToNext()){
            items = new HashMap<String, String>();
            items.put("null", "");
            items.put("id", c.getString(0));
            items.put("name", c.getString(1));
            items.put("points",c.getString(2));
            arrayList.add(items);

        }


        String from[]={"null","name","points"};
        int to[] = {R.id.idText, R.id.taskText, R.id.pointText};

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                arrayList,
                R.layout.list_row,
                from, to );


        adapter.setDropDownViewResource(R.layout.list_row);
        spinner.setAdapter(adapter);
    }


    private void SentRequest(){

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                HashMap element = (HashMap) arrayList.get(position);
                taskId =  Integer.parseInt(element.get("id").toString());
                taskName =  element.get("name").toString();
                taskPoints =  Integer.parseInt(element.get("points").toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                HashMap element = (HashMap) arrayList.get(parent.getFirstVisiblePosition());
                taskId =  Integer.parseInt(element.get("id").toString());
                taskName =  element.get("name").toString();
                taskPoints =  Integer.parseInt(element.get("points").toString());
            }
        });



        button.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        boolean isSent = emailDb.insertEmailData(userData.getData().getInt(0) ,
                                userData.getData().getInt(6),
                                taskName,
                                taskPoints);
                        if (isSent == true) {
                            Toast.makeText(getActivity(), "Data sent", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Data not sent", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
