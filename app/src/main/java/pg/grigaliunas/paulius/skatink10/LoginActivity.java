package pg.grigaliunas.paulius.skatink10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    public static final String Extra_Text = "IDfromTable";

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

    private void openMainWindow() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.warningText);
                String id = mydb.ValidateByUserName(String.valueOf(username.getText()), password.getText().toString());
                if( id != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(Extra_Text, id);
                    startActivity(intent);
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
