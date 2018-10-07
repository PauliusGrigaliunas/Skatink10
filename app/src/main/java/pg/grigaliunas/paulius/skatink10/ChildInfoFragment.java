package pg.grigaliunas.paulius.skatink10;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseChild;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildInfoFragment extends Fragment {

    private DatabaseHelper mydb;
    private int id;
    private Button deleteBtn;
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
        DeleteObject();
        return view;
    }

    private void DeleteObject() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDeleted =mydb.delete(id);

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
