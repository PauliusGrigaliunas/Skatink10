package pg.grigaliunas.paulius.skatink10.parent;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pg.grigaliunas.paulius.skatink10.LoginActivity;
import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseParent;
import pg.grigaliunas.paulius.skatink10.UserData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerFragment extends Fragment {


    private DatabaseHelper mydb;
    private UserData userData = UserData.getInstance();
    private Button deleteBtn;
    private TextView nameView, emailView;

    public ManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mydb = new DatabaseParent(getActivity());
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        deleteBtn = (Button) view.findViewById(R.id.deleteBtn);
        nameView = (TextView) view.findViewById(R.id.nameView);
        emailView= (TextView) view.findViewById(R.id.emailView);
        setUser();
        deleteObject();
        return view;
    }


    public void setUser(){

        nameView.setText(userData.getData().getString(3)+ " "
                + userData.getData().getString(7));
        emailView.setText(userData.getData().getString(8));
    }

    private void deleteObject() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted = mydb.delete(userData.getData().getInt(0));

                if ( isDeleted == true) {
                    Toast.makeText(getActivity(), "Data deleted", Toast.LENGTH_LONG).show();
                    userData.clear();
                    goBack();
                }
                else {
                    Toast.makeText(getActivity(), "Data not deleted", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void goBack() {
        this.getActivity().finish();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }
}
