package jaavajaava.smarthome;


        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.TextView;
        import android.widget.Button;
        import android.view.View;

public class Stereot extends AppCompatActivity {

    TextView kappale;
    static TextView vola;
    Button ylos;
    Button alas;
    String song = "nyt soi humppa";//databaselisays tarvitaan
    static int volume = 5;//databaselisays tarvitaan
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stereot);
        kappale = (TextView)findViewById(R.id.textView6);
        vola = (TextView)findViewById(R.id.textView7);
        ylos = (Button)findViewById(R.id.kovaa);
        alas = (Button)findViewById(R.id.hiljaa);

        ylos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stereot.lisaaAanta();
            }
        });

        alas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stereot.vahennaAanta();
            }
        });

        String a = "Volume " + volume;
        vola.setText(a);
        kappale.setText(song);

    }

    public static void lisaaAanta(){
        volume = volume + 1;
        String a = "Volume " + volume;
        vola.setText(a);
        //pitaa lisata database tallennus
    }

    public static void vahennaAanta(){
        volume = volume - 1;
        String a = "Volume " + volume;
        vola.setText(a);
        //pitaa lisata database tallennus
    }

}
