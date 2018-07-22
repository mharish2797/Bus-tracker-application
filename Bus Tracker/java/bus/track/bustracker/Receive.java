package bus.track.bustracker;

/**
 *		Created on: 21-09-2016  
 *      Author: Harish Mohan
 */

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class Receive  extends AsyncTask<String,Void,String>{
    private TextView roleField;
    private Context context;


    //flag 0 means get and 1 means post.(By default it is get.)
    public Receive(Context context,TextView roleField) {
        this.context = context;
        this.roleField = roleField;

    }

    protected void onPreExecute(){

    }

    @Override
    protected String doInBackground(String... arg0) {


            try {
                String busnum = (String) arg0[0];
                String date = (String)arg0[1];
                String time= (String)arg0[2];
                String link = "http://www.mitcommuterpass.net16.net/jtempset.php?busnum=" + busnum+"&date="+date+"&time="+time;

                URL url = new URL(link);
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(link));
                HttpResponse response = client.execute(request);
                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();


                return sb.toString();
            }

            catch(Exception e){

                return "Exception: " + e.getMessage();
            }


    }

    @Override
    public void onPostExecute(String result){
       // this.statusField.setText("Login Successful");

       // this.roleField.setText(result);
       // return s;
    }
}
