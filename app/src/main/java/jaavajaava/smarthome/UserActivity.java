package jaavajaava.smarthome;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    ListView listView;
    long uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listView = (ListView)findViewById(R.id.userView);
        Intent intent = getIntent();
        uid = intent.getLongExtra("EXTRA_UID", 0);
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

            EstateCursorAdapter adapter = new EstateCursorAdapter(getApplicationContext(), cursor, 0);
            listView.setAdapter(adapter);
        }

        @Override
        protected Cursor doInBackground(Void... params) {
            SmarthomeOpenHelper db = new SmarthomeOpenHelper(getApplicationContext());
            return db.getUserEstates(uid);
        }
    }

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
    };
}
