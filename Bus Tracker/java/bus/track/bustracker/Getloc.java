package bus.track.bustracker;
/**
 *		Created on: 21-09-2016  
 *      Author: Harish Mohan
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Getloc extends AppCompatActivity implements View.OnClickListener {
    Button jsendcontent,jconduct,jtermin;
    boolean set=false;
    private ProgressBar jupload;
    private Handler mHandler = new Handler();
    String lats="",longs="",jnum="",zeus="";
    EditText jbusnum;
    double latit=0,longit=0;
    int m=0; Context ctx=this;
    int i=1;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getloc);
        getSupportActionBar();
        jsendcontent=(Button)findViewById(R.id.sendcontent);
        jsendcontent.setOnClickListener(this);
        jupload=(ProgressBar)findViewById(R.id.upload);
        jupload.setVisibility(View.GONE);
        jbusnum=(EditText)findViewById(R.id.busnum);
        jbusnum.setText(null);
        jconduct=(Button)findViewById(R.id.conductor);
        jconduct.setOnClickListener(this);
        jtermin=(Button)findViewById(R.id.stopcon);
        jtermin.setOnClickListener(this);
        jtermin.setVisibility(View.GONE);
        intent=new Intent(this,MyService.class);

    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.sendcontent){
           jupload.setVisibility(View.VISIBLE);
           m++;
           GPSTracker gps;
           gps=new GPSTracker(getApplicationContext());
           if (gps.canGetLocation()) {

               latit = gps.getLatitude();
               //latit+=0.001;
               longit = gps.getLongitude();
               longit-=0.001;
               lats = String.valueOf(latit);
               longs = String.valueOf(longit);
               String macAddress=Settings.Secure.getString(this.getContentResolver(),
                       Settings.Secure.ANDROID_ID);
               jnum = jbusnum.getText().toString();
               if (jnum.matches("")) Toast.makeText(this, "Enter a bus number", Toast.LENGTH_LONG).show();
               else {
                   if (latit > 0.0 && longit > 0.0) {

                       boolean f = send(jnum, lats, longs,macAddress);

                       Toast.makeText(this, "Thankyou for the updation ", Toast.LENGTH_LONG).show();
                   }
               }
           }
           else Toast.makeText(this, "Unable to access location. Try again!", Toast.LENGTH_LONG).show();
       }

        else if(v.getId()==R.id.conductor){
           GPSTracker gps;
           gps=new GPSTracker(getApplicationContext());
           if (gps.canGetLocation()) {

               latit = gps.getLatitude();
               latit+=0.001;
               longit = gps.getLongitude();
               longit-=0.001;
               lats = String.valueOf(latit);
               longs = String.valueOf(longit);
               String macAddress=Settings.Secure.getString(this.getContentResolver(),
                       Settings.Secure.ANDROID_ID);
               jnum = jbusnum.getText().toString();
               if (jnum.matches("")) Toast.makeText(this, "Enter a bus number", Toast.LENGTH_LONG).show();
               else {
                   if (latit > 0.0 && longit > 0.0) {

                       intent.putExtra("Lat",lats);
                       intent.putExtra("Lon",longs);
                       intent.putExtra("Busnum",jnum);
                       intent.putExtra("MAC",macAddress);
                       jconduct.setVisibility(View.GONE);
                       jtermin.setVisibility(View.VISIBLE);
                       startService(intent);
                       Toast.makeText(this, "Starting Service... ", Toast.LENGTH_LONG).show();
                   }
                   else Toast.makeText(this, lats+" "+longs, Toast.LENGTH_LONG).show();
               }
           }
           else Toast.makeText(this, "Unable to access location. Try again!", Toast.LENGTH_LONG).show();



       }
        else{
           jconduct.setVisibility(View.VISIBLE);
           jtermin.setVisibility(View.GONE);
           stopService(intent);
            System.exit(0);

       }


    }



    boolean send(String busnum,String latit,String longit,String Mac){
        Background b=new Background();
        b.execute(busnum,latit,longit,Mac);
        //Toast.makeText(this, busnum+" "+latit+" "+longit+" "+Mac, Toast.LENGTH_LONG).show();
        return true;
    }

    class Background extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {
            Intent intent1 = new Intent();
           intent1.setClass(ctx, Type.class);
            startActivity(intent1);
            finish();
        }


        @Override
        public String doInBackground(String... params) {

            String bbus=params[0],data="";
            String blat=params[1],blong=params[2],bmac=params[3];
            int tp;
            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yy");
                SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm");
                String dat=sdf1.format(new Date());
                String tim=sdf5.format(new Date());
               // Toast.makeText(this,s+" "+s1,Toast.LENGTH_LONG).show();

                URL url= new URL("http://mitcommuterpass.net16.net/jtrack.php");
                String urlparams="busnum="+bbus+"&latitude="+blat+"&longitude="+blong+"&date="+dat+"&time="+tim+"&mac="+bmac;
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                os.write(urlparams.getBytes());
                os.flush();
                os.close();
                InputStream is=httpURLConnection.getInputStream();
                while((tp=is.read())!=-1){
                    data+=(char)tp;
                }

                is.close();
                httpURLConnection.disconnect();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

}
