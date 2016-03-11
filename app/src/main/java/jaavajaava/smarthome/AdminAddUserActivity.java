package jaavajaava.smarthome;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AdminAddUserActivity extends AppCompatActivity {

    TextView mName;
    TextView mPass;
    Button mAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);

        mName = (TextView) findViewById(R.id.newName);
        mPass = (TextView) findViewById(R.id.newPassword);
        mAddUser = (Button) findViewById(R.id.openAddUser);

        mAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddUserAsyncTask().execute(mName.getText().toString(), mPass.getText().toString());
            }
        });
    }

    private class AddUserAsyncTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            finish();
        }

        @Override
        protected Void doInBackground(String... params) {
            String name = params[0];
            String pass = params[1];
            SmarthomeOpenHelper db = new SmarthomeOpenHelper(getApplicationContext());
            db.addUser(name, pass);
            return null;
        }
    }
}
