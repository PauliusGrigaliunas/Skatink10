package pg.grigaliunas.paulius.skatink10.parent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseAssigment;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseChild;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseEmail;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationInfoFragment extends Fragment {

    private DatabaseHelper mydb, childDb, emailDb;
    private int childId, nr, points;
    private String name, task;
    private Button rejectBtn, acceptBtn;
    private TextView nameView;
    public NotificationInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_info, container, false);
        mydb = new DatabaseAssigment(getActivity());
        childDb = new DatabaseChild(getActivity());
        emailDb = new DatabaseEmail(getActivity());
        childId = getArguments().getInt("id");
        nr = getArguments().getInt("nr");
        name = getArguments().getString("name");
        task = getArguments().getString("task");
        points = getArguments().getInt("points");
        rejectBtn = (Button) view.findViewById(R.id.rejectBtn);
        acceptBtn = (Button) view.findViewById(R.id.acceptBtn);
        nameView = (TextView) view.findViewById(R.id.nameView);
        Accept();
        Reject();
        return view;
    }


    private void Accept() {
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getActivity(), name , Toast.LENGTH_LONG).show();
                boolean isInserted = mydb.insertAssignmentData(childId, nr, true);

                if ( isInserted == true) {
                    boolean isUpdated = childDb.addPoints(childId, points);
                    if ( isUpdated == true) {
                        Toast.makeText(getActivity(), "Data added", Toast.LENGTH_LONG).show();
                        goBack();
                    }
                    else Toast.makeText(getActivity(), "Data not added", Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(getActivity(), "Data not added", Toast.LENGTH_LONG).show();

            }
        });
    }
    private void Reject() {
        rejectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = mydb.insertAssignmentData(childId, nr, false);
                if ( isInserted == true) {
                    Toast.makeText(getActivity(), "Data added", Toast.LENGTH_LONG).show();
                    goBack();
                }
                else {
                    Toast.makeText(getActivity(), "Data not added", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void goBack() {
        emailDb.delete(nr);
        Fragment fragment = new NotificationFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();

    }
}
