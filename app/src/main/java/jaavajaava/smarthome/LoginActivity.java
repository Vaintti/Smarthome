package jaavajaava.smarthome;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Database";
    EditText name;
    EditText pass;
    Button sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = (EditText)findViewById(R.id.name);
        pass = (EditText)findViewById(R.id.password);
        sign = (Button)findViewById(R.id.button);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoginAsyncTask().execute(name.getText().toString(), pass.getText().toString());
            }
        });
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Log.d(TAG, aBoolean.booleanValue()+"");
            if(name.getText().toString().equals("admin") && pass.getText().toString().equals("admin")) {
                Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(i);
            }
            else if(aBoolean.booleanValue()) {
                Intent i = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(i);
            }
            else {
                Toast.makeText(LoginActivity.this, "Please check your login info.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            SmarthomeOpenHelper db = new SmarthomeOpenHelper(getApplicationContext());
            if(db.getAllUsers().size() == 0) {
                User user = new User("admin", "admin");
                db.addUser(user);
            }
            return db.foundFromDatabase(username, password);
        }
    }
}
