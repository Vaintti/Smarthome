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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AdminUserActivity extends AppCompatActivity {

    ListView estates;
    EstateCursorAdapter adapter;
    ImageButton addEstate;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        estates = (ListView) findViewById(R.id.adminEstateList);
        addEstate = (ImageButton) findViewById(R.id.addEstateButton);
        Intent intent = getIntent();
        id = intent.getLongExtra("EXTRA_USERID", 0);

        estates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        addEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminUserActivity.this, AdminAddEstateActivity.class);
                i.putExtra("EXTRA_UID", id);
                startActivity(i);
            }
        });


    }

    /*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        if(view.getId() == R.id.adminEstateList) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            String[] menuItems = {"", "Remove estate"};
        }
    }
    */

    @Override
    protected void onResume() {
        super.onResume();

        new UpdateAsyncTask().execute(id);
    }

    /**
     * AsyncTask to get estates to cursor
     */

    private class UpdateAsyncTask extends AsyncTask<Long, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            if(cursor != null) {
                adapter = new EstateCursorAdapter(getApplicationContext(), cursor, 0);
                estates.setAdapter(adapter);
            }
        }

        @Override
        protected Cursor doInBackground(Long... params) {
            SmarthomeOpenHelper db = new SmarthomeOpenHelper(getApplicationContext());

            return db.getUserEstates(params[0].longValue());
        }
    }

    /**
     * Cursoradapter to put items to list
     */

    public class EstateCursorAdapter extends CursorAdapter {

        public EstateCursorAdapter(Context context, Cursor cursor, int flags) {
            super(context, cursor, 0);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(R.layout.item_estate, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView tvName = (TextView) view.findViewById(R.id.estateNameView);
            TextView tvAddress = (TextView) view.findViewById(R.id.estateAddressView);

            String name = cursor.getString(cursor.getColumnIndexOrThrow(SmarthomeContract.Estate.COLUMN_NAME_ESTATENAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(SmarthomeContract.Estate.COLUMN_NAME_ADDRESS));

            tvName.setText(name);
            tvAddress.setText(address);
        }
    }
}
