package pg.grigaliunas.paulius.skatink10;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseParent;
import pg.grigaliunas.paulius.skatink10.parent.MainActivity;
import pg.grigaliunas.paulius.skatink10.parent.UserData;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper mydb;
    private EditText username, password;
    private Button loginBtn, signupBtn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb = new DatabaseParent(this);
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
                Cursor cursor = mydb.ValidateByUserName(String.valueOf(username.getText()), password.getText().toString());
                if( cursor!= null){
                    UserData userData = UserData.getInstance();
                    userData.setData(cursor);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else tv.setText( " incorrect username or password " );
            }
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
