package pg.grigaliunas.paulius.skatink10;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import pg.grigaliunas.paulius.skatink10.child.MainChildActivity;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseChild;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseParent;
import pg.grigaliunas.paulius.skatink10.parent.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper mydb , childb;
    private EditText username, password;
    private Button loginBtn, signupBtn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb = new DatabaseParent(this);
        childb = new DatabaseChild(this);
        username = (EditText) findViewById(R.id.usernameText1);
        password = (EditText) findViewById(R.id.passwordText1);
        loginBtn = (Button) findViewById(R.id.loginButton);
        signupBtn = (Button) findViewById(R.id.signupButton);
        tv = (TextView) findViewById(R.id.warningText);
        openMainWindow();
        openSignupWindow();

    }


    private void openMainWindow() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = mydb.findByUserName(String.valueOf(username.getText()), password.getText().toString());
                if (cursor != null) {
                    if(cursor.getInt(4) == 1){
                        cursor = mydb.validateByUserName(String.valueOf(username.getText()), password.getText().toString());
                        UserData userData = UserData.getInstance();
                        userData.setData(cursor);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else {
                        cursor = childb.validateByUserName(String.valueOf(username.getText()), password.getText().toString());
                        UserData userData = UserData.getInstance();
                        userData.setData(cursor);
                        startActivity(new Intent(LoginActivity.this, MainChildActivity.class));
                    }
                    } else tv.setText(" incorrect username or password ");}
        });

    }

    private void openSignupWindow() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
}
