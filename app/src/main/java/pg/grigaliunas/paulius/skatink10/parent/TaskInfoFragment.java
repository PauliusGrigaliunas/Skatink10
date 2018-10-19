package pg.grigaliunas.paulius.skatink10.parent;


import android.annotation.TargetApi;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseTask;
import pg.grigaliunas.paulius.skatink10.parent.TaskFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment {

    private DatabaseTask mydb;
    
    private int id;
    private Button deleteBtn, addPointsBtn, NamBarBtnVar;
    private EditText numberText;
    private TextView nameView;
    private FloatingActionButton fab;
    public TaskInfoFragment() {
        // Required empty public constructor
    }

    private Toolbar toolbar;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mydb = new DatabaseTask(getActivity());
        id = getArguments().getInt("data");
        View view = inflater.inflate(R.layout.fragment_task_info, container, false);
        deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        addPointsBtn = (Button) view.findViewById(R.id.addpointsBtn);
        nameView = (TextView) view.findViewById(R.id.nameView);
        numberText = (EditText) view.findViewById(R.id.numberText);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        Cursor cursor = mydb.findDataById(id);
        nameView.setText(cursor.getString(1));


        changeToolBar();
        deleteObject();
        addPoints();
        returnBack();
        return view;
    }

    private void changeToolBar(){
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setSubtitle("Task info");

        NamBarBtnVar = new Button(getActivity());
        NamBarBtnVar.setText("delete");
        android.support.v7.widget.Toolbar.LayoutParams layoutParams = new android.support.v7.widget.Toolbar.LayoutParams(android.support.v7.widget.Toolbar.LayoutParams.WRAP_CONTENT, android.support.v7.widget.Toolbar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity=Gravity.END;
        toolbar.addView(NamBarBtnVar, layoutParams);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
    }


    private void addPoints() {
        addPointsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean isUpdated = mydb.addPoints(id, Integer.parseInt(numberText.getText().toString()));

                if ( isUpdated == true) {
                    Toast.makeText(getActivity(), "Data added", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(), "Data not deleted", Toast.LENGTH_LONG).show();
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
        Fragment fragment = new TaskFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();
    }
}
