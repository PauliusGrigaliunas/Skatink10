package pg.grigaliunas.paulius.skatink10.parent;

import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pg.grigaliunas.paulius.skatink10.R;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseHelper;
import pg.grigaliunas.paulius.skatink10.dataBase.DatabaseTask;

public class TaskActivity extends AppCompatActivity {

    public DatabaseHelper mydb;
    private EditText name, point;
    private Button addTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mydb = new DatabaseTask(this);
        name = (EditText) findViewById(R.id.taskText);
        point = (EditText) findViewById(R.id.pointText);
        addTaskBtn = (Button) findViewById(R.id.addButton);
        createTask();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void createTask() {
        addTaskBtn.setOnClickListener(
             new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(name.getText().toString().matches(".+")) {
                         boolean isInserted = mydb.insertTaskData(
                                 name.getText().toString(),
                                 Integer.parseInt(point.getText().toString())
                         );
                         if (isInserted == true) {
                             Toast.makeText(TaskActivity.this, "Data inserted", Toast.LENGTH_LONG).show();
                         } else {
                             Toast.makeText(TaskActivity.this, "Data not inserted", Toast.LENGTH_LONG).show();
                         }
                     }
                     else{
                         Toast.makeText(TaskActivity.this, "not enough symbols", Toast.LENGTH_LONG).show();
                     }
                 }
             });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) this.finish();
        return super.onOptionsItemSelected(item);
    }
}
