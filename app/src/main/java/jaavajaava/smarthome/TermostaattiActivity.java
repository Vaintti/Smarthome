package jaavajaava.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
/**
 * Created by Heikki on 30/03/2016.
 */
public class TermostaattiActivity extends AppCompatActivity {

    TextView nakyma;
    Button ylos;
    Button alas;
    int lampo = 35;//tahan taytyy lisata se databasesta haku

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termostaatti);

        nakyma = (TextView) findViewById(R.id.textView3);
        ylos = (Button) findViewById(R.id.button2);
        alas = (Button) findViewById(R.id.button3);


        ylos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermostaattiActivity.lisaaLampoa();
            }
        });

        alas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermostaattiActivity.vahennaLampoa();
            }
        });
        String a = "" + lampo;
        nakyma.setText(a);
    }
        public static void lisaaLampoa() {
            lampo = lampo + 1;
            String a = "" + lampo;
            nakyma.setText(a);
            //pitaa lisata tallennus databaseen
        }
        public static void vahennaLampoa() {
            lampo = lampo - 1;
            String a = "" + lampo;
            nakyma.setText(a);
            //pitaa lisata tallennus databaseen
        }
    }


