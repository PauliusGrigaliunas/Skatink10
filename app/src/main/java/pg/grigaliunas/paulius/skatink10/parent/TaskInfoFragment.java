package pg.grigaliunas.paulius.skatink10.parent;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskInfoFragment extends Fragment {

    private DatabaseTask mydb;
    
    private int id;
    private Button addPointsBtn, NamBarBtnVar;
    private EditText numberText;
    private TextView nameView;
    private Toolbar toolbar;


    public TaskInfoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_info, container, false);
        mydb = new DatabaseTask(getActivity());
        id = getArguments().getInt("data");

        addPointsBtn = (Button) view.findViewById(R.id.addpointsBtn);
        NamBarBtnVar = new Button(getActivity());
        nameView = (TextView) view.findViewById(R.id.nameView);
        numberText = (EditText) view.findViewById(R.id.numberText);
        Cursor cursor = mydb.findDataById(id);
        nameView.setText(cursor.getString(1));



        changeToolBar();
        deleteObject();
        addPoints();
        return view;
    }




    private void changeToolBar(){

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        toolbar.setSubtitle("Task info");
        NamBarBtnVar.setText("delete");
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity=Gravity.END;
        toolbar.addView(NamBarBtnVar, layoutParams);
        }

    public void  onDestroyView() {
        super.onDestroyView();
        toolbar.removeView(NamBarBtnVar);
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
    private void goBack() {
        Fragment fragment = new TaskFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();
    }
}
