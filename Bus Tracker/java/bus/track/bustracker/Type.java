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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by HP on 18-09-2016.
 */
public class Type extends AppCompatActivity implements View.OnClickListener {
    RadioGroup jmode;
    Button jproceed;
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.type);
        getSupportActionBar();
        jproceed=(Button)findViewById(R.id.proceed);
        jproceed.setOnClickListener(this);
        jmode=(RadioGroup)findViewById(R.id.mode);
        jmode.check(R.id.user);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        int select=jmode.getCheckedRadioButtonId();
        if(select==R.id.user)
        {   intent.setClass(this,Checkroute.class); startActivity(intent);}
        else {
            if (!isNetworkAvailable()) {
                LinearLayout layout=(LinearLayout)findViewById(R.id.linerlayer);

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

            }
            else {
                intent.setClass(this, Getloc.class);
                startActivity(intent);
            }
        }


    }
}
