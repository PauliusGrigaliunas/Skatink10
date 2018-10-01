package pg.grigaliunas.paulius.skatink10;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildFragment extends Fragment {

    private DatabaseHelper mydb;
    private UserData userData = UserData.getInstance();
    private ListView listView;
    private FloatingActionButton fab;

    public ChildFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);

        mydb = new DatabaseHelper(getActivity());
        listView = (ListView) view.findViewById(R.id.listView);

        List listItems = mydb.findChilds(userData.getData().getInt(0));

       /* SimpleAdapter adapter = new SimpleAdapter(this, listItems,
                        R.layout.list_row,
                        new String[]{"name", "designation", "location"},
                        new int[]{R.id.name, R.id.designation, R.id.location});
*/


        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        openChildActivity();

        return view;
    }

    private void openChildActivity() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChildActivity.class));
            }
        });
    }
}
