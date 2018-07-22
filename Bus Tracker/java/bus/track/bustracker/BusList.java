package bus.track.bustracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HP on 18-09-2016.
 */
public class BusList extends AppCompatActivity {
    TextView jdisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buslist);
        getSupportActionBar();
        Bundle bundle=getIntent().getExtras();
        ArrayList<String> zs = (ArrayList<String>) getIntent().getSerializableExtra("List");
        zs=bundle.getStringArrayList("List");
        jdisp=(TextView)findViewById(R.id.disp);
        String s="\n";
        for(int i=0;i<zs.size();i++){
            s+=zs.get(i)+"\n\n";
        }
        jdisp.setText(s);
    }
    }
