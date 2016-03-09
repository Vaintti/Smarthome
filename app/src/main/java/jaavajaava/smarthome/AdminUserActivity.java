package jaavajaava.smarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class AdminUserActivity extends AppCompatActivity {

    ListView estates;
    ImageButton addEstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        estates = (ListView) findViewById(R.id.adminEstateList);
        addEstate = (ImageButton) findViewById(R.id.addEstateButton);

        estates.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        addEstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminUserActivity.this, AdminAddEstateActivity.class);
                startActivity(i);
            }
        });
    }
}
