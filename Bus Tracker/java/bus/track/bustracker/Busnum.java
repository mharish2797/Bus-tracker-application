package bus.track.bustracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

/**
 * Created by HP on 18-09-2016.
 */
public class Busnum extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    Button jtrack;
    String busnum="17H";
    TextView role;
    Spinner jbusspin;
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busnum);
        getSupportActionBar();
        jtrack=(Button)findViewById(R.id.track);
        jtrack.setOnClickListener(this);
        role=(TextView)findViewById(R.id.role);
        Bundle bundle=getIntent().getExtras();
        ArrayList<String> zs = (ArrayList<String>) getIntent().getSerializableExtra("List");
        zs=bundle.getStringArrayList("List");
        jbusspin=(Spinner)findViewById(R.id.spinbus);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinneritem, zs);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jbusspin.setAdapter(dataAdapter);
        jbusspin.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        busnum=parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {

        /*****place this snackbar code inside no internet connectivity case**********/
        if (!isNetworkAvailable()) {
            LinearLayout layout=(LinearLayout)findViewById(R.id.linlay);

          Snackbar snackbar = Snackbar.make(layout, "No internet connection!", Snackbar.LENGTH_INDEFINITE).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentz=new Intent();
                    intentz.setClass(ctx,MainActivity.class);
                    startActivity(intentz);
                }
            });

// Changing message text color

            snackbar.setActionTextColor(Color.RED);

// Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
          //  Toast.makeText(this, "Check your internet connectivity", Toast.LENGTH_LONG).show();

        } else {
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
            SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm");
            String s = sdf1.format(new Date());
            String s1 = sdf5.format(new Date());
            String bnum = busnum;
            Toast.makeText(this, "Searching " + busnum + " around your location..", Toast.LENGTH_LONG).show();
            String fc = "";
            try {
                fc = new Receive(this, role).execute(bnum, s, s1).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            // role.setText("");
 //           Toast.makeText(this, fc, Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(this, Mapbus.class);
            StringTokenizer st = new StringTokenizer(fc);
            String lta[] = new String[100];
            Integer i = 0;
            while (st.hasMoreElements()) {
                lta[i++] = st.nextToken(" ");
                lta[i++] = st.nextToken(" ");
                lta[i++] = st.nextToken(" ");

            }
            intent.putExtra("N", i);
            intent.putExtra("BusNum", bnum);
            for (Integer j = 0; j < i; j++) {
                intent.putExtra(String.valueOf(j), lta[j]);
            }

            // role.setText(fc);

            startActivity(intent);
        }

    }

}
