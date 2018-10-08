package pg.grigaliunas.paulius.skatink10;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseChild;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildInfoFragment extends Fragment {

    private DatabaseChild mydb;
    private int id;
    private Button deleteBtn, addPointsBtn;
    private EditText numberText;
    private TextView nameView;

    public ChildInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mydb = new DatabaseChild(getActivity());
        id = getArguments().getInt("data");
        View view = inflater.inflate(R.layout.fragment_child_info, container, false);
        deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        addPointsBtn = (Button) view.findViewById(R.id.addpointsBtn);
        nameView = (TextView) view.findViewById(R.id.nameView);
        numberText = (EditText) view.findViewById(R.id.numberText);
        Cursor cursor = mydb.findDataById(id);
        nameView.setText(cursor.getString(3));
        DeleteObject();
        AddPoints();
        return view;
    }

    private void AddPoints() {
        addPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isUpdated = mydb.addPoints(id, Integer.parseInt(numberText.getText().toString()));

                if ( isUpdated == true) {
                    Toast.makeText(getActivity(), "Data added", Toast.LENGTH_LONG).show();
                    /*Fragment fragment = new ChildFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.mainFrame, fragment);
                    ft.commit();*/

                    //addPoints();
                }
                else {
                    Toast.makeText(getActivity(), "Data not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void DeleteObject() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = mydb.delete(id);

                if ( isDeleted == true) {
                    Toast.makeText(getActivity(), "Data deleted", Toast.LENGTH_LONG).show();
                    Fragment fragment = new ChildFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.mainFrame, fragment);
                    ft.commit();
                }
                else {
                    Toast.makeText(getActivity(), "Data not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
