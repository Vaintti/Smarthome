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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {

    private static final String TAG = "AdminActivity";
    ListView listView;
    ImageButton createUser;
    NameCursorAdapter adapter;
    private String[] menuItems;
    private int selectedItemPosition;
    private String selectedItemName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        //new AdminAsyncTask().execute();
        menuItems = new String[1];
        //menuItems[0] = "Change password";
        menuItems[0] = "Remove user";

        listView = (ListView)findViewById(R.id.userList);
        createUser = (ImageButton) findViewById(R.id.addButton);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AdminActivity.this, AdminUserActivity.class);
                i.putExtra("EXTRA_USERID", id);
                //i.putExtra("EXTRA_USERID", adapter.getItemId(position));
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

        registerForContextMenu(listView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AdminAsyncTask().execute();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        if(view.getId() == R.id.userList) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            selectedItemPosition = info.position;

            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        String selectedItem = menuItems[menuItemIndex];

        if(selectedItem.equals("Remove user")){
            new RemoveUserAsyncTask().execute(adapter.getItemId(selectedItemPosition));
        }
        return true;
    }

    private class AdminAsyncTask extends AsyncTask<String, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            adapter = new NameCursorAdapter(getApplicationContext(), cursor, 0);
            listView.setAdapter(adapter);
        }

        @Override
        protected Cursor doInBackground(String... params) {
            SmarthomeOpenHelper db = new SmarthomeOpenHelper(getApplicationContext());
            return db.getUsers();
        }
    }

    private class RemoveUserAsyncTask extends AsyncTask<Long, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            // If tried to remove admin
            if(aBoolean) {
                Toast.makeText(getApplicationContext(), "Cannot remove admin user.", Toast.LENGTH_SHORT).show();
            }
            // Update the view after removing is done
            new AdminAsyncTask().execute();
        }

        @Override
        protected Boolean doInBackground(Long... params) {
            SmarthomeOpenHelper db = new SmarthomeOpenHelper(getApplicationContext());
            return db.deleteUser(params[0].longValue());
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

            //String name = cursor.getString(cursor.getColumnIndexOrThrow(SmarthomeContract.User.COLUMN_NAME_USERNAME));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(SmarthomeContract.User.COLUMN_NAME_USERNAME));

            tvName.setText(name);
        }
    }
}