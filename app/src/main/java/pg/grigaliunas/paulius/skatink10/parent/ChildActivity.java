package pg.grigaliunas.paulius.skatink10.parent;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseChild;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;
import pg.grigaliunas.paulius.skatink10.UserData;

public class ChildActivity extends AppCompatActivity {

    private DatabaseHelper mydb;
    private UserData userData = UserData.getInstance();
    private Button addChildBtn;
    private EditText usernameText, passwordText, nameText;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mydb = new DatabaseChild(this);

        usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        nameText = (EditText) findViewById(R.id.taskText);
        addChildBtn = (Button) findViewById(R.id.addChildBtn);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        AddData();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        returnBack();

    }
    public void AddData() {
        addChildBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = mydb.insertChildData(
                                userData.getData().getInt(6),
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

    private void returnBack() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void goBack() {
        this.finish();
    }
}
