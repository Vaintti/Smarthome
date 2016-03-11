package jaavajaava.smarthome;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

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

    private class LoginAsyncTask extends AsyncTask<String, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor aCursor) {
            super.onPostExecute(aCursor);

            if(aCursor.moveToFirst()) {
                if(aCursor.getString(aCursor.getColumnIndex(SmarthomeContract.User.COLUMN_NAME_USERNAME)).equals("admin")) {
                    Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                    i.putExtra("EXTRA_UID", aCursor.getLong(aCursor.getColumnIndex(SmarthomeContract.User._ID)));
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(LoginActivity.this, UserActivity.class);
                    i.putExtra("EXTRA_UID", aCursor.getLong(aCursor.getColumnIndex(SmarthomeContract.User._ID)));
                    startActivity(i);
                }
            }
            else {
                Toast.makeText(LoginActivity.this, "Please check your login info.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected Cursor doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            SmarthomeOpenHelper db = new SmarthomeOpenHelper(getApplicationContext());

            return db.getUserWithPassword(username, password);
        }
    }
}
