package bus.track.bustracker;
/**
 *		Created on: 21-09-2016  
 *      Author: Harish Mohan
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RadioGroup jcontent;
    Context ctx=this;
    Button jgo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         //Toast.makeText(this, "Project by \nHarish M (2014503517)", Toast.LENGTH_LONG).show();
        jgo=(Button)findViewById(R.id.go);
        jgo.setOnClickListener(this);
        getSupportActionBar();
        jcontent=(RadioGroup)findViewById(R.id.content);
        jcontent.check(R.id.offline);
                }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        int select=jcontent.getCheckedRadioButtonId();
        if(select==R.id.offline)
        intent.setClass(this,SourceDest.class);
        else intent.setClass(this,Type.class);
        startActivity(intent);

    }


}
