package jaavajaava.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class KosteusMittari extends AppCompatActivity {
    TextView nakyma;
    int prosentti = 45;//databasehaun lisays
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kosteus_mittari);
        nakyma = (TextView) findViewById(R.id.textView2);

        String a = "" + prosentti + "%";
        nakyma.setText(a);
    }
}
