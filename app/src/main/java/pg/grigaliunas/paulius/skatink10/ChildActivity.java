package pg.grigaliunas.paulius.skatink10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChildActivity extends AppCompatActivity {

    private DatabaseHelper mydb;
    private UserData userData = UserData.getInstance();
    private Button addChildBtn;
    private EditText usernameText, passwordText, nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mydb = new DatabaseHelper(this);

        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        nameText = (EditText) findViewById(R.id.nameText);
        addChildBtn = (Button) findViewById(R.id.addChildBtn);
        AddData();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void AddData() {
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
                            Toast.makeText(ChildActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(ChildActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) this.finish();
        return super.onOptionsItemSelected(item);
    }
}
