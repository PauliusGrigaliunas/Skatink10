package pg.grigaliunas.paulius.skatink10;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildFragment extends Fragment {

    private MainActivity mainActivity = new MainActivity();
    private DatabaseHelper mydb;
    private UserData userData = UserData.getInstance();
    private Button addChildBtn;
    private EditText usernameText, passwordText, nameText;
    private TextView textView;

    public ChildFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);

        mydb = new DatabaseHelper(getActivity());


        usernameText = (EditText) view.findViewById(R.id.usernameText1);
        passwordText = (EditText) view.findViewById(R.id.passwordText1);
        nameText = (EditText) view.findViewById(R.id.nameText1);
        addChildBtn = (Button) view.findViewById(R.id.addChildBtn);
        textView = (TextView) view.findViewById(R.id.textView);

        //openSignupWindow();
        AddData();
        return view;
    }

    private void openSignupWindow() {
        addChildBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText( " buttonClicked " );

            }
        });
    }

    public void AddData(){
        addChildBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = mydb.insertChildData(
                                userData.getData().getInt(0),
                                usernameText.getText().toString(),
                                passwordText.getText().toString(),
                                nameText.getText().toString());
                        if (isInserted == true) {
                            Toast.makeText(getActivity(), "Data inserted", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Data not inserted", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }
}
