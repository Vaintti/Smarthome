package jaavajaava.smarthome;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private static final String TAG = "AdminActivity";
    ListView listView;
    ImageButton createUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //new AdminAsyncTask().execute();

        listView = (ListView)findViewById(R.id.userList);
        createUser = (ImageButton) findViewById(R.id.addButton);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AdminActivity.this, AdminUserActivity.class);
                startActivity(i);
            }
        });

        createUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this, AdminAddUserActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AdminAsyncTask().execute();
    }

    private class AdminAsyncTask extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            NameCursorAdapter adapter = new NameCursorAdapter(getApplicationContext(), cursor, 0);
            listView.setAdapter(adapter);
        }

        @Override
        protected Cursor doInBackground(Void... params) {
            SmarthomeOpenHelper db = new SmarthomeOpenHelper(getApplicationContext());
            return db.getUsernames();
        }
    }

    public class NameCursorAdapter extends CursorAdapter {
        public NameCursorAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tvName = (TextView) view.findViewById(R.id.userName);

            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

            tvName.setText(name);
        }
    };
}