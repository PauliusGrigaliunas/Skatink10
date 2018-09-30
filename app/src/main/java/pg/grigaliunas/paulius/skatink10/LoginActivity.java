package pg.grigaliunas.paulius.skatink10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    private DatabaseHelper mydb;
    private EditText username, password;
    private Button loginBtn, signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mydb = new DatabaseHelper(this);
        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);
        loginBtn = (Button) findViewById(R.id.loginButton);
        signupBtn = (Button) findViewById(R.id.signupButton);
        openMainWindow();
        openSignupWindow();
    }

    public void openMainWindow() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.warningText);
                if(mydb.Validate(String.valueOf(username.getText()), password.getText().toString())!= null)
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                else tv.setText( " incorrect username or password " );
            }
        });

    }

    public void openSignupWindow() {
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
}
