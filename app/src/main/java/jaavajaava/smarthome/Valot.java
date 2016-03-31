package jaavajaava.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;
import  android.widget.CompoundButton;

public class Valot extends AppCompatActivity {

    ToggleButton nappi;
    static TextView valot;
    int tila = 1;//database haku valoille joko 1 tai 0 riippuen onko paalla vai ei

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valot);
        nappi = (ToggleButton) findViewById(R.id.toggleButton);
        valot = (TextView) findViewById(R.id.textView2);

        if (tila == 0) {
            nappi.setChecked(false);
            valot.setText("valot poissa paalta");
        }

        if (tila == 1) {
            nappi.setChecked(true);
            valot.setText("Valot paalla");
        }
        nappi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Valot.asetaValotPaalle();
                } else {
                    Valot.asetaValotPois();
                }
            }

        });
    }

    public static void asetaValotPois() {
        valot.setText("valot poissa paalta");
        //talennus databaseen tassa kohdassa
    }

    public static void asetaValotPaalle() {
        valot.setText("Valot paalla");
        //talennus databaseen tassa kohdassa
    }


}

