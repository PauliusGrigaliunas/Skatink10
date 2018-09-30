package pg.grigaliunas.paulius.skatink10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private DatabaseHelper mydb;
    private EditText username, password, name, surname, email, phone;
    private Button addbtn, newbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mydb = new DatabaseHelper(this);

        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        name = (EditText) findViewById(R.id.nameText);
        surname = (EditText) findViewById(R.id.surnameText);
        email = (EditText) findViewById(R.id.emailText);
        phone = (EditText) findViewById(R.id.phoneText);
        addbtn = (Button) findViewById(R.id.addBtn);
        AddData();
    }

    public void AddData(){
        addbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = mydb.insertParentData(
                                username.getText().toString(),
                                password.getText().toString(),
                                name.getText().toString(),
                                surname.getText().toString(),
                                email.getText().toString(),
                                phone.getText().toString()
                        );
                        if (isInserted == true) {
                            Toast.makeText(SignupActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(SignupActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }
}
