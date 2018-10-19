package pg.grigaliunas.paulius.skatink10.parent;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseChild;
import pg.grigaliunas.paulius.skatink10.parent.ChildFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildInfoFragment extends Fragment {

    private DatabaseChild mydb, assignDb;
    private int id;
    private Button deleteBtn, addPointsBtn, NamBarBtnVar;
    private EditText numberText, editText;
    private TextView nameView;
    private FloatingActionButton fab;

    public ChildInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_child_info, container, false);
       // View rootView = inflater.inflate(R.layout.activity_main, container, false);
        mydb = new DatabaseChild(getActivity());
        assignDb = new DatabaseChild(getActivity());
        id = getArguments().getInt("data");
        deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        addPointsBtn = (Button) view.findViewById(R.id.addpointsBtn);
        nameView = (TextView) view.findViewById(R.id.nameView);
        numberText = (EditText) view.findViewById(R.id.numberText);
        editText = (EditText) view.findViewById(R.id.editText);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        Cursor cursor = mydb.findDataById(id);
        nameView.setText(cursor.getString(3));



        changeToolBar();


        deleteObject();
        addPoints();
        returnBack();
        return view;
    }

    private void changeToolBar(){
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setSubtitle("Child info");

        NamBarBtnVar = new Button(getActivity());
        NamBarBtnVar.setText("delete");
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity=Gravity.END;
        toolbar.addView(NamBarBtnVar, layoutParams);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void addPoints() {
        addPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = mydb.insertAssignmentData(id, editText.getText().toString(),
                        Integer.parseInt(numberText.getText().toString()), true);
                boolean isUpdated = mydb.addPoints(id, Integer.parseInt(numberText.getText().toString()));

                if ( isUpdated == true) {
                    Toast.makeText(getActivity(), "Data added", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "Data not added", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void deleteObject() {
        NamBarBtnVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = mydb.delete(id);

                if ( isDeleted == true) {
                    Toast.makeText(getActivity(), "Data deleted", Toast.LENGTH_LONG).show();
                    goBack();
                }
                else {
                    Toast.makeText(getActivity(), "Data not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void returnBack() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }
    private void goBack() {
        Fragment fragment = new ChildFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();
    }

}
