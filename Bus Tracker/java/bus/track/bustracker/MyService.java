package bus.track.bustracker;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyService extends Service {
    int i=17;
    String later="",longer="",busnum="",macer="";
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

      return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Intent in=Intent.getIntent();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long timer=3600000;
        Bundle bundle = intent.getExtras();
        later=bundle.getString("Lat");
        longer=bundle.getString("Lon");
        busnum=bundle.getString("Busnum");
        macer=bundle.getString("MAC");


        new CountDownTimer(timer, 60000) {

            public void onTick(long millisUntilFinished) {
                i*=2;
               // Toast.makeText(getApplicationContext(), i + " "+later+" "+longer+" "+busnum+" "+macer, Toast.LENGTH_SHORT).show();
             boolean f= send(later,longer,busnum,macer);
            }
            public void onFinish() {

            }
        }.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Tasks Completed !", Toast.LENGTH_SHORT).show();
        this.stopSelf();
    }
    boolean send(String latit,String longit,String busnum,String Mac){
        Background1 b=new Background1();
        b.execute(busnum, latit, longit,Mac);
        return true;
    }

    class Background1 extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String s) {

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
