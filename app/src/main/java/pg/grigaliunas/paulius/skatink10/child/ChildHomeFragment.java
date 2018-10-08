package pg.grigaliunas.paulius.skatink10.child;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.UserData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildHomeFragment extends Fragment {

    private UserData userData = UserData.getInstance();
    private TextView scoreView;

    public ChildHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_home, container, false);
        scoreView = (TextView) view.findViewById(R.id.scoreView);
        scoreView.setText(userData.getData().getString(8));


        return view;

    }

}
